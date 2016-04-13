package com.smartdevicelink.api;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermissionManager;

public abstract class SdlActivity extends SdlContextAbsImpl {

    private static final String TAG = SdlActivity.class.getSimpleName();

    public static final int FLAG_DEFAULT = 0;
    public static final int FLAG_CLEAR_HISTORY = 1;
    public static final int FLAG_CLEAR_TOP = 2;

    enum SdlActivityState{
        PRE_CREATE,
        POST_CREATE,
        BACKGROUND,
        FOREGROUND,
        STOPPED,
        DESTROYED
    }

    private SdlActivityState mActivityState = SdlActivityState.PRE_CREATE;

    private boolean isFinishing = false;

    private boolean superCalled;
    private boolean isBackHandled;

    final void initialize(SdlContext sdlContext){
        if(!isInitialized()) {
            setSdlApplicationContext(sdlContext);
            setAndroidContext(sdlContext.getAndroidApplicationContext());
            setInitialized(true);
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

    @CallSuper
    protected void onCreate(){
        superCalled = true;
    }

    @CallSuper
    protected void onRestart(){
        superCalled = true;
    }

    @CallSuper
    protected void onStart(){
        superCalled = true;
    }

    @CallSuper
    protected void onForeground(){
        superCalled = true;
    }

    @CallSuper
    protected void onBackground(){
        superCalled = true;
    }

    @CallSuper
    protected void onStop(){
        superCalled = true;
    }

    @CallSuper
    protected void onDestroy() {
        superCalled = true;
    }

    protected final void finish(){
        ((SdlApplication)getSdlApplicationContext()).getSdlActivityManager().finish();
    }

    public void onBackNavigation(){
        isBackHandled = false;
    }

    final SdlActivityState getActivityState(){
        return mActivityState;
    }

    final boolean isFinishing() {
        return isFinishing;
    }

    final void setIsFinishing(boolean isFinishing) {
        this.isFinishing = isFinishing;
    }

    final void performCreate(){
        superCalled = false;
        mActivityState = SdlActivityState.POST_CREATE;
        this.onCreate();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onCreate(). This should NEVER happen.");
    }

    final  void performRestart(){
        superCalled = false;
        mActivityState = SdlActivityState.POST_CREATE;
        this.onRestart();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onRestart(). This should NEVER happen.");
    }

    final void performStart(){
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        this.onStart();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStart(). This should NEVER happen.");
    }

    final void performForeground(){
        superCalled = false;
        mActivityState = SdlActivityState.FOREGROUND;
        this.onForeground();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onForeground(). This should NEVER happen.");
    }

    final void performBackground(){
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        this.onBackground();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onBackground(). This should NEVER happen.");
    }

    final void performStop(){
        superCalled = false;
        mActivityState = SdlActivityState.STOPPED;
        this.onStop();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStop(). This should NEVER happen.");
    }

    final void performDestroy(){
        superCalled = false;
        mActivityState = SdlActivityState.DESTROYED;
        this.onDestroy();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onDestroy(). This should NEVER happen.");
    }

    final boolean performBackNavigation(){
        isBackHandled = true;
        this.onBackNavigation();
        return isBackHandled;
    }

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        getSdlApplicationContext().startSdlActivity(activity, flags);
    }

    @Override
    public SdlPermissionManager getSdlPermissionManager() {
        return getSdlApplicationContext().getSdlPermissionManager();
    }

    public class SuperNotCalledException extends RuntimeException{

        SuperNotCalledException(String detailMessage){
            super(detailMessage);
        }

    }
}
