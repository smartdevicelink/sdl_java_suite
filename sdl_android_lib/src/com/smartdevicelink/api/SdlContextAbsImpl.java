package com.smartdevicelink.api;

import android.content.Context;
import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;

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

    void initialize(SdlContext sdlContext){
        if(!isInitialized) {
            mAndroidContext = sdlContext.getAndroidApplicationContext();
            mSdlApplicationContext = sdlContext;
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
            this.getClass().getCanonicalName());
        }
    }

    void initialize(Context androidContext){
        if(!isInitialized) {
            mAndroidContext = androidContext;
            mSdlApplicationContext = this;
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

}
