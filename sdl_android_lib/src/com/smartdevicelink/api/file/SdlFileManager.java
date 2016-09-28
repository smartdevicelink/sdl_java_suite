package com.smartdevicelink.api.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smartdevicelink.api.SdlApplication;
import com.smartdevicelink.api.SdlApplicationConfig;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class SdlFileManager implements SdlApplication.LifecycleListener{

    private static final String TAG = SdlFileManager.class.getSimpleName();

    private final HashSet<String> mFileSet;

    private final SdlApplication mSdlApplication;
    private final SdlApplicationConfig mSdlApplicationConfig;
    private boolean isReady = false;
    private LinkedList<ImageTaskObject> mPendingRequests = new LinkedList<>();
    private final Handler mExecutionHandler;

    public SdlFileManager(SdlApplication sdlApplication, SdlApplicationConfig config){
        mSdlApplication = sdlApplication;
        mExecutionHandler = new Handler(mSdlApplication.getSdlExecutionLooper());
        mSdlApplicationConfig = config;
        mFileSet = new HashSet<>();
    }

    public boolean uploadSdlImage(@NonNull final SdlImage sdlImage, @Nullable final FileReadyListener listener){
        Log.d(TAG, "SdlImage isForceReplace = " + sdlImage.isForceReplace());
        if(!sdlImage.isForceReplace() && mFileSet.contains(sdlImage.getSdlName())){
            return true;
        }

        ImageTaskObject taskObject = new ImageTaskObject(sdlImage, listener);

        if(!isReady){
            Log.d(TAG, "Adding " + sdlImage.getSdlName() + " to mPendingRequests");
            mPendingRequests.add(taskObject);
        } else {
            Log.d(TAG, "Uploading image: " + sdlImage.getSdlName() + " to mPendingRequests");
            new LoadImageTask().execute(taskObject);
        }
        return false;
    }

    public boolean isFileOnModule(String sdlFileName){
        return mFileSet.contains(sdlFileName);
    }

    @Override
    public void onSdlConnect() {
        Log.d(TAG, "onSdlConnect");
        if(mSdlApplicationConfig.getAppIcon() != null){
            sendListFiles();
        }
    }

    @Override
    public void onBackground() {

    }

    @Override
    public void onForeground() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void onSdlDisconnect() {

    }

    private void uploadAppIcon(){
        uploadSdlImage(mSdlApplicationConfig.getAppIcon(), new FileReadyListener() {
            @Override
            public void onFileReady(SdlFile sdlFile) {
                setAppIcon();
            }

            @Override
            public void onFileError(SdlFile sdlFile) {
                Log.d(TAG, "Unable to upload App Icon.");
            }
        });
    }

    private void setAppIcon() {
        SetAppIcon sai = new SetAppIcon();
        sai.setSdlFileName(mSdlApplicationConfig.getAppIcon().getSdlName());
        mSdlApplication.sendRpc(sai);
    }

    private void sendListFiles(){
        ListFiles listFiles = new ListFiles();
        listFiles.setOnRPCResponseListener(mListFileResponseListener);
        mSdlApplication.sendRpc(listFiles);
    }

    private OnRPCResponseListener mListFileResponseListener = new OnRPCResponseListener() {
        @Override
        public void onResponse(int correlationId, RPCResponse response) {
            if(response == null) return;

            ListFilesResponse lfr = (ListFilesResponse) response;
            List<String> fileNames = lfr.getFilenames();
            if(fileNames != null) {
                for (String fileName : fileNames) {
                    mFileSet.add(fileName);
                }
            }

            if(mSdlApplicationConfig.getAppIcon().isForceReplace() ||
                    !mFileSet.contains(mSdlApplicationConfig.getAppIcon().getSdlName())){
                uploadAppIcon();
            } else {
                setAppIcon();
            }

            if(!isReady){
                handlePendingRequests();
                isReady = true;
            }
        }
    };

    private void handlePendingRequests(){
        while(!mPendingRequests.isEmpty()){
            ImageTaskObject taskObject = mPendingRequests.removeFirst();
            if(mFileSet.contains(taskObject.image.getSdlName()) && taskObject.listener != null){
                taskObject.listener.onFileReady(taskObject.image);
            } else {
                new LoadImageTask().execute(taskObject);
            }
        }
    }

    public interface FileReadyListener{

        void onFileReady(SdlFile sdlFile);

        void onFileError(SdlFile sdlFile);

    }

    private class LoadImageTask extends AsyncTask<ImageTaskObject, Void, Void>{

        @Override
        protected Void doInBackground(ImageTaskObject... params) {
            final SdlImage sdlImage = params[0].image;
            final FileReadyListener listener = params[0].listener;

            final PutFile putFile = new PutFile();
            putFile.setFileType(FileType.GRAPHIC_PNG);
            putFile.setSdlFileName(sdlImage.getSdlName());
            putFile.setPersistentFile(sdlImage.isPersistent());

            boolean isFileLoaded = false;

            if(sdlImage.getResId() != null) {
                isFileLoaded = loadImageFromResource(sdlImage.getResId(), putFile);
            } else if(sdlImage.getPath() != null){
                isFileLoaded = loadImageFromPath(sdlImage.getPath(), putFile);
            } else if(sdlImage.getURL() != null){
                isFileLoaded = loadImageFromURL(sdlImage.getURL(), putFile);
            } else {
                Log.w(TAG, "No image sent to module because no identifiers were provided.");
                return null;
            }

            if(!isFileLoaded){
                Log.w(TAG, "No image sent to module because we are unable to load the file.");
                return null;
            }

            if (listener != null) {
                putFile.setOnRPCResponseListener(new OnRPCResponseListener() {
                    @Override
                    public void onResponse(int correlationId, RPCResponse response) {
                        if (response.getSuccess()) {
                            mFileSet.add(sdlImage.getSdlName());
                            listener.onFileReady(sdlImage);
                        } else {
                            listener.onFileError(sdlImage);
                        }
                    }
                });
            }

            mExecutionHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSdlApplication.sendRpc(putFile);
                }
            });
            return null;
        }

        private boolean loadImageFromResource(int resId, PutFile putFile) {
            Bitmap image = BitmapFactory.decodeResource(mSdlApplication.getAndroidApplicationContext().getResources(), resId);
            if(image == null){
                Log.w(TAG, "Unable to get bitmap from resource ID: " + resId);
                return false;
            }
            sendBitmapPutfile(putFile, image);
            Log.i(TAG, "Loaded bitmap from resource ID: " + resId);
            return true;
        }

        private boolean loadImageFromPath(File path, PutFile putFile) {
            Bitmap image = BitmapFactory.decodeFile(path.getAbsolutePath());
            if(image == null){
                Log.w(TAG, "Unable to get bitmap from path: " + path.getAbsolutePath());
                return false;
            }
            sendBitmapPutfile(putFile, image);
            Log.i(TAG, "Loaded bitmap from path: " + path.getAbsolutePath());
            return true;
        }

        private boolean loadImageFromURL(URL url, PutFile putFile) {
            BufferedInputStream is;
            try {
                URLConnection connection = url.openConnection();
                is = new BufferedInputStream(connection.getInputStream());
            } catch (IOException e) {
                Log.w(TAG, "Unable to open connection to: " + url.toExternalForm());
                e.printStackTrace();
                return false;
            }

            Bitmap image = BitmapFactory.decodeStream(is);
            if(image == null){
                Log.w(TAG, "Unable to get bitmap from URL: " + url.toExternalForm());
                return false;
            }
            sendBitmapPutfile(putFile, image);
            Log.i(TAG, "Loaded bitmap from URL: " + url.toExternalForm());
            return true;
        }

        private void sendBitmapPutfile(PutFile putFile, Bitmap image) {
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bas);
            byte[] data = bas.toByteArray();
            putFile.setBulkData(data);
        }

    }

    private class ImageTaskObject {
        final SdlImage image;
        final FileReadyListener listener;

        ImageTaskObject(SdlImage image, FileReadyListener listener){
            this.image = image;
            this.listener = listener;
        }
    }

}
