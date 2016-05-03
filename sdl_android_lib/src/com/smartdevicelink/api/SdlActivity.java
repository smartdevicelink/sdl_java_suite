package com.smartdevicelink.api;

import android.support.annotation.CallSuper;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.api.view.SdlButton;
import com.smartdevicelink.api.view.SdlView;
import com.smartdevicelink.api.view.SdlViewManager;
import com.smartdevicelink.proxy.RPCRequest;

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

    private SdlViewManager mViewManager;

    final void initialize(SdlContext sdlContext){
        if(!isInitialized()) {
            setSdlApplicationContext(sdlContext);
            setAndroidContext(sdlContext.getAndroidApplicationContext());
            mViewManager = new SdlViewManager(this);
            setInitialized(true);
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

    public void setContentView(SdlView view){
        mViewManager.setRootView(view);
        view.setDisplayCapabilities(((SdlApplication)getSdlApplicationContext()).getDisplayCapabilities());
    }

    @CallSuper
    protected void onCreate(){
        superCalled = true;
    }

    @CallSuper
    protected void onCreateViews(){
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
        performCreateViews();
    }

    final void performCreateViews(){
        superCalled = false;
        this.onCreateViews();
        if(!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onCreateViews(). This should NEVER happen.");
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
        mViewManager.getRootView().setIsVisible(true);
        mViewManager.updateView();
        mViewManager.prepareImages();
    }

    final void performBackground(){
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        mViewManager.getRootView().setIsVisible(false);
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
    public int registerButtonCallback(SdlButton.OnPressListener listener) {
        return getSdlApplicationContext().registerButtonCallback(listener);
    }

    @Override
    public void unregisterButtonCallback(int id) {
        getSdlApplicationContext().unregisterButtonCallback(id);
    }

    @Override
    public boolean sendRpc(RPCRequest request) {
        return getSdlApplicationContext().sendRpc(request);
    }

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        getSdlApplicationContext().startSdlActivity(activity, flags);
    }

    @Override
    public SdlFileManager getSdlFileManager() {
        return getSdlApplicationContext().getSdlFileManager();
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
