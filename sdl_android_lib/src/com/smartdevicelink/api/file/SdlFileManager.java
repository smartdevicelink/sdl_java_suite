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

    private static final String SDL_APP_ICON_NAME = "sdl_app_icon";

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
        if(mSdlApplicationConfig.getAppIconResId() != null){
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
            for(String fileName: fileNames){
                mFileSet.add(fileName);
            }

            if(!mFileSet.contains(SDL_APP_ICON_NAME)){
                uploadAppIcon();
            } else {
                setAppIcon();
            }
        }
    };

    private void uploadAppIcon(){
        uploadPersistentImageFile(SDL_APP_ICON_NAME, mSdlApplicationConfig.getAppIconResId(), new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response != null && response.getSuccess()){
                    setAppIcon();
                }
            }
        });
    }

    private void setAppIcon() {
        SetAppIcon sai = new SetAppIcon();
        sai.setSdlFileName(SDL_APP_ICON_NAME);
        mSdlApplication.sendRpc(sai);
    }

    private void sendListFiles(){
        ListFiles listFiles = new ListFiles();
        listFiles.setOnRPCResponseListener(mListFileResponseListener);
        mSdlApplication.sendRpc(listFiles);
    }

    public void uploadPersistentImageFile(String sdlFileName, int resId, OnRPCResponseListener listener){
        if(mFileSet.contains(sdlFileName)) return;

        PutFile file = new PutFile();
        file.setFileType(FileType.GRAPHIC_PNG);
        file.setSdlFileName(sdlFileName);
        file.setPersistentFile(true);

        Bitmap image = BitmapFactory.decodeResource(mSdlApplication.getAndroidApplicationContext().getResources(), resId);
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bas);
        byte[] data = bas.toByteArray();
        file.setBulkData(data);

        file.setOnRPCResponseListener(listener);

        mSdlApplication.sendRpc(file);
    }
}
