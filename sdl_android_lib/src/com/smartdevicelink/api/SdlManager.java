package com.smartdevicelink.api;

import android.content.Context;

import java.util.HashMap;

public class SdlManager {

    private SdlManager mInstance;
    private Context mAndroidContext;

    private HashMap<String, SdlApplicationConfig> mApplicationConfigRegistry;

    private boolean isPrepared = false;

    private SdlManager(){
        mApplicationConfigRegistry = new HashMap<>();
    }

    public SdlManager getInstance(){
        if(mInstance == null){
            mInstance = new SdlManager();
        }

        return mInstance;
    }

    public boolean registerSdlApplication(SdlApplicationConfig config){
        mApplicationConfigRegistry.put(config.getAppId(), config);
        return true;
    }

    public boolean unregisterSdlApplication(String appId){
        SdlApplicationConfig config = mApplicationConfigRegistry.remove(appId);
        return config == null;
    }

    public void prepare(Context context){

    }

    void onBluetoothConnected(){

    }

}
