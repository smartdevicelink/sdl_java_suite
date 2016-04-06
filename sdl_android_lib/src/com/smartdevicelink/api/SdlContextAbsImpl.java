package com.smartdevicelink.api;

import android.content.Context;

import com.smartdevicelink.api.interfaces.SdlContext;

abstract class SdlContextAbsImpl implements SdlContext {

    private SdlContext mSdlApplicationContext;
    private Context mAndroidContext;

    protected SdlContextAbsImpl(Context androidContext){
        mAndroidContext = androidContext;
        mSdlApplicationContext = this;
    }

    protected SdlContextAbsImpl(SdlContext sdlApplicationContext){
        mAndroidContext = sdlApplicationContext.getAndroidApplicationContext();
        mSdlApplicationContext = sdlApplicationContext;
    }

    @Override
    public final SdlContext getSdlApplicationContext(){
        return mSdlApplicationContext;
    }

    @Override
    public final Context getAndroidApplicationContext(){
        return mAndroidContext;
    }

}
