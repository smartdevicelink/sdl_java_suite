package com.smartdevicelink.api;

import android.content.Context;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.menu.SdlMenuManager;
import com.smartdevicelink.api.menu.SdlMenuTransaction;

abstract class SdlContextAbsImpl implements SdlContext {

    private static final String TAG = SdlContextAbsImpl.class.getSimpleName();

    private boolean isInitialized = false;

    private SdlContext mSdlApplicationContext;
    private Context mAndroidContext;

    @Override
    public final SdlContext getSdlApplicationContext(){
        return mSdlApplicationContext;
    }

    @Override
    public final Context getAndroidApplicationContext(){
        return mAndroidContext;
    }

    @Override
    public SdlMenuTransaction beginGlobalMenuTransaction() {
        return mSdlApplicationContext.beginGlobalMenuTransaction();
    }

    @Override
    public SdlMenuManager getSdlMenuManager() {
        return mSdlApplicationContext.getSdlMenuManager();
    }

    final void setSdlApplicationContext(SdlContext sdlContext){
        mSdlApplicationContext = sdlContext;
    }

    final void setAndroidContext(Context context){
        mAndroidContext = context;
    }

    final void setInitialized(boolean isInitialized){
        this.isInitialized = isInitialized;
    }

    final boolean isInitialized(){
        return isInitialized;
    }

}
