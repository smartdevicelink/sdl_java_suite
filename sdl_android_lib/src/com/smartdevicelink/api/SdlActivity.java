package com.smartdevicelink.api;

import android.content.Context;
import android.support.annotation.CallSuper;

public abstract class SdlActivity implements SdlContext {

    enum SdlActivityState{
        created,
        started,
        foreground,
        background,
        stopped,
        restarted,
        destroyed
    }

    private final SdlContext mSdlApplicationContext;

    SdlActivityState mActivityState;

    SdlActivity(SdlApplication sdlApplicationContext){
        mSdlApplicationContext = sdlApplicationContext;
    }

    @CallSuper
    public void onCreate(){

    }

    @CallSuper
    public void onRestart(){

    }

    @CallSuper
    public void onStart(){

    }

    @CallSuper
    public void onForeground(){

    }

    @CallSuper
    public void onBackground(){

    }

    @CallSuper
    public abstract void onStop();

    @CallSuper
    public abstract void onDestroy();


    /****************************
     SdlContext interface methods
     ****************************/

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        mSdlApplicationContext.startSdlActivity(activity, flags);
    }

    @Override
    public final SdlContext getSdlApplicationContext() {
        return mSdlApplicationContext;
    }

    @Override
    public final Context getAndroidApplicationContext() {
        return mSdlApplicationContext.getAndroidApplicationContext();
    }

}
