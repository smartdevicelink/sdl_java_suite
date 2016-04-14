package com.smartdevicelink.api.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;

public class SdlFileManager implements SdlApplication.LifecycleListener{

    private static final String TAG = SdlFileManager.class.getSimpleName();

    private final HashSet<String> mFileSet;

    private final SdlApplication mSdlApplication;
    private final SdlApplicationConfig mSdlApplicationConfig;

    public SdlFileManager(SdlApplication sdlApplication, SdlApplicationConfig config){
        mSdlApplication = sdlApplication;
        mSdlApplicationConfig = config;
        mFileSet = new HashSet<>();
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

    private OnRPCResponseListener mListFileResponseListener = new OnRPCResponseListener() {
        @Override
        public void onResponse(int correlationId, RPCResponse response) {
            if(response == null) return;
            try {
                Log.i(TAG, response.serializeJSON().toString(3));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ListFilesResponse lfr = (ListFilesResponse) response;
            List<String> fileNames = lfr.getFilenames();
            if(fileNames != null) {
                for (String fileName : fileNames) {
                    mFileSet.add(fileName);
                }
            }

            if(!mFileSet.contains(mSdlApplicationConfig.getAppIcon().getSdlName())){
                uploadAppIcon();
            } else {
                setAppIcon();
            }
        }
    };

    private void uploadAppIcon(){
        uploadSdlImage(mSdlApplicationConfig.getAppIcon(), new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                try {
                    Log.i(TAG, response.serializeJSON().toString(3));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(response != null && response.getSuccess()){
                    setAppIcon();
                }
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

    public void uploadSdlImage(SdlImage sdlImage, OnRPCResponseListener listener){
        if(!sdlImage.isForceReplace() && mFileSet.contains(sdlImage.getSdlName())) return;

        if(sdlImage.getResId() != null) {

            PutFile file = new PutFile();
            file.setFileType(FileType.GRAPHIC_PNG);
            file.setSdlFileName(sdlImage.getSdlName());
            file.setPersistentFile(sdlImage.isPersistent());
            Bitmap image = BitmapFactory.decodeResource(mSdlApplication.getAndroidApplicationContext().getResources(), sdlImage.getResId());
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bas);
            byte[] data = bas.toByteArray();
            file.setBulkData(data);

            file.setOnRPCResponseListener(listener);

            mSdlApplication.sendRpc(file);
        }
    }
}
