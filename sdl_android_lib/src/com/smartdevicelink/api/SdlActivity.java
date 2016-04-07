package com.smartdevicelink.api;

import android.support.annotation.CallSuper;

public abstract class SdlActivity extends SdlContextAbsImpl {

    enum SdlActivityState{
        PRE_CREATE,
        POST_CREATE,
        BACKGROUND,
        FOREGROUND,
        STOPPED,
        DESTROYED
    }

    private SdlActivityState mActivityState = SdlActivityState.PRE_CREATE;

    private boolean superCalled;

    @CallSuper
    public void onCreate(){
        superCalled = true;
        mActivityState = SdlActivityState.POST_CREATE;
    }

    @CallSuper
    public void onRestart(){
        superCalled = true;
        mActivityState = SdlActivityState.POST_CREATE;
    }

    @CallSuper
    public void onStart(){
        superCalled = true;
        mActivityState = SdlActivityState.BACKGROUND;
    }

    @CallSuper
    public void onForeground(){
        superCalled = true;
        mActivityState = SdlActivityState.FOREGROUND;
    }

    @CallSuper
    public void onBackground(){
        superCalled = true;
        mActivityState = SdlActivityState.BACKGROUND;
    }

    @CallSuper
    public void onStop(){
        superCalled = true;
        mActivityState = SdlActivityState.STOPPED;
    }

    @CallSuper
    public void onDestroy(){
        superCalled = true;
        mActivityState = SdlActivityState.DESTROYED;
    }

    final SdlActivityState getActivityState(){
        return mActivityState;
    }

    final void performCreate(){
        superCalled = false;
        this.onCreate();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onCreate(). This should NEVER happen.");
    }

    final  void performRestart(){
        superCalled = false;
        this.onRestart();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onRestart(). This should NEVER happen.");
    }

    final void performStart(){
        superCalled = false;
        this.onStart();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStart(). This should NEVER happen.");
    }

    final void performForeground(){
        superCalled = false;
        this.onForeground();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onForeground(). This should NEVER happen.");
    }

    final void performBackground(){
        superCalled = false;
        this.onBackground();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onBackground(). This should NEVER happen.");
    }

    final void performStop(){
        superCalled = false;
        this.onStop();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStop(). This should NEVER happen.");
    }

    final void performDestroy(){
        superCalled = false;
        this.onDestroy();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onDestroy(). This should NEVER happen.");
    }

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        getSdlApplicationContext().startSdlActivity(activity, flags);
    }

    public class SuperNotCalledException extends RuntimeException{

        SuperNotCalledException(String detailMessage){
            super(detailMessage);
        }

    }
}
