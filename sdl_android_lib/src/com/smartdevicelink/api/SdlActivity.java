package com.smartdevicelink.api;

import android.support.annotation.CallSuper;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlActivity extends SdlContextAbsImpl {

    enum SdlActivityState{
        created,
        started,
        foreground,
        background,
        stopped,
        restarted,
        destroyed
    }

    SdlActivityState mActivityState;

    SdlActivity(SdlContext sdlApplicationContext){
        super(sdlApplicationContext);
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

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        getSdlApplicationContext().startSdlActivity(activity, flags);
    }
}
