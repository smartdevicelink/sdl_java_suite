package com.smartdevicelink.api;

import android.support.annotation.CallSuper;

public class SdlActivity implements SdlContext {

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
    public void onStop(){

    }

    @CallSuper
    public void onDestroy(){

    }

}
