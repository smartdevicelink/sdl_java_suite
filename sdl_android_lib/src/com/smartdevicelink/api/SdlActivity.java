package com.smartdevicelink.api;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.menu.SdlMenuManager;
import com.smartdevicelink.api.menu.SdlMenuTransaction;
import com.smartdevicelink.api.menu.SelectListener;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.api.view.SdlAudioPassThruDialog;
import com.smartdevicelink.api.view.SdlButton;
import com.smartdevicelink.api.view.SdlChoiceSetManager;
import com.smartdevicelink.api.view.SdlView;
import com.smartdevicelink.api.view.SdlViewManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

public abstract class SdlActivity extends SdlContextAbsImpl {

    private static final String TAG = SdlActivity.class.getSimpleName();

    public static final int FLAG_DEFAULT = 0;
    public static final int FLAG_CLEAR_HISTORY = 1;
    public static final int FLAG_CLEAR_TOP = 2;
    public static final int FLAG_PULL_TO_TOP = 3;

    enum SdlActivityState {
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

    final void initialize(SdlContext sdlContext) {
        if (!isInitialized()) {
            setSdlApplicationContext(sdlContext);
            setAndroidContext(sdlContext.getAndroidApplicationContext());
            mViewManager = new SdlViewManager(this);
            setInitialized(true);
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

    public void setContentView(SdlView view) {
        mViewManager.setRootView(view);
        view.setDisplayCapabilities(getSdlApplicationContext().getDisplayCapabilities());
    }

    @CallSuper
    protected void onCreate(@Nullable Bundle bundle) {
        superCalled = true;
    }

    @CallSuper
    protected void onCreateViews() {
        superCalled = true;
    }

    @CallSuper
    protected void onRestart(){
        superCalled = true;
    }

    @CallSuper
    protected void onStart() {
        superCalled = true;
    }

    @CallSuper
    protected void onForeground() {
        superCalled = true;
    }

    @CallSuper
    protected void onBackground() {
        superCalled = true;
    }

    @CallSuper
    protected void onStop() {
        superCalled = true;
    }

    @CallSuper
    protected void onDestroy() {
        superCalled = true;
    }

    protected final void finish() {
        ((SdlApplication) getSdlApplicationContext()).getSdlActivityManager().finish();
    }


    public final void onBackNavigation() {
        isBackHandled = false;
    }

    final SdlActivityState getActivityState() {
        return mActivityState;
    }

    final boolean isFinishing() {
        return isFinishing;
    }

    final void setIsFinishing(boolean isFinishing) {
        this.isFinishing = isFinishing;
    }

    final void performCreate(Bundle bundle) {
        superCalled = false;
        mActivityState = SdlActivityState.POST_CREATE;
        this.onCreate(bundle);
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onCreate(). This should NEVER happen.");
        performCreateViews();
    }

    final void performCreateViews() {
        superCalled = false;
        this.onCreateViews();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onCreateViews(). This should NEVER happen.");
    }

    final void performRestart() {
        superCalled = false;
        mActivityState = SdlActivityState.POST_CREATE;
        this.onRestart();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onRestart(). This should NEVER happen.");
    }

    final void performStart(String currentTemplate) {
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        getSdlMenuManager().redoTransactions(this);
        mViewManager.setCurrentTemplate(currentTemplate);
        mViewManager.updateView();
        this.onStart();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStart(). This should NEVER happen.");
    }

    final void performForeground() {
        superCalled = false;
        mActivityState = SdlActivityState.FOREGROUND;
        this.onForeground();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onForeground(). This should NEVER happen.");
        mViewManager.getRootView().setIsVisible(true);
        mViewManager.updateView();
        mViewManager.prepareImages();
    }

    final void performBackground() {
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        mViewManager.getRootView().setIsVisible(false);
        this.onBackground();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onBackground(). This should NEVER happen.");
    }

    final String performStop() {
        superCalled = false;
        mActivityState = SdlActivityState.STOPPED;
        this.onStop();
        getSdlMenuManager().undoTransactions(this);
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStop(). This should NEVER happen.");
        return mViewManager.getCurrentTemplate();
    }

    final void performDestroy() {
        superCalled = false;
        mActivityState = SdlActivityState.DESTROYED;
        this.onDestroy();
        getSdlMenuManager().clearTransactionRecord(this);
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onDestroy(). This should NEVER happen.");
    }

    final boolean performBackNavigation() {
        isBackHandled = true;
        this.onBackNavigation();
        return isBackHandled;
    }


    public final SdlMenuTransaction beginLocalMenuTransaction() {
        return new SdlMenuTransaction(this, this);
    }

    @Override
    public final int registerButtonCallback(SdlButton.OnPressListener listener) {
        return getSdlApplicationContext().registerButtonCallback(listener);
    }

    @Override
    public final void unregisterButtonCallback(int id) {
        getSdlApplicationContext().unregisterButtonCallback(id);
    }

    @Override
    public final boolean sendRpc(RPCRequest request) {
        return getSdlApplicationContext().sendRpc(request);
    }

    @Override
    public final void registerAudioPassThruListener(SdlAudioPassThruDialog.ReceiveDataListener listener) {
        getSdlApplicationContext().registerAudioPassThruListener(listener);
    }

    @Override
    public final void unregisterAudioPassThruListener(SdlAudioPassThruDialog.ReceiveDataListener listener) {
        getSdlApplicationContext().unregisterAudioPassThruListener(listener);
    }

    @Override
    public final SdlPermissionManager getSdlPermissionManager() {
        return getSdlApplicationContext().getSdlPermissionManager();
    }

    public final SdlFileManager getSdlFileManager() {
        return getSdlApplicationContext().getSdlFileManager();
    }

    @Override
    public final void registerMenuCallback(int id, SelectListener listener) {
        getSdlApplicationContext().registerMenuCallback(id, listener);
    }

    @Override
    public final void unregisterMenuCallback(int id) {
        getSdlApplicationContext().unregisterMenuCallback(id);
    }

    @Override
    public final SdlChoiceSetManager getSdlChoiceSetManager(){
        return  getSdlApplicationContext().getSdlChoiceSetManager();
    }

    @Override
    public final Looper getSdlExecutionLooper() {
        return getSdlApplicationContext().getSdlExecutionLooper();
    }

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, Bundle bundle, int flags) {
        getSdlApplicationContext().startSdlActivity(activity, bundle, flags);
    }

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        startSdlActivity(activity, null, flags);
    }

    @Override
    public final SdlMenuManager getSdlMenuManager() {
        return getSdlApplicationContext().getSdlMenuManager();
    }

    @Override
    public final SdlMenuTransaction beginGlobalMenuTransaction() {
        return getSdlApplicationContext().beginGlobalMenuTransaction();
    }

    @Override
    public final void registerRpcNotificationListener(FunctionID functionID, OnRPCNotificationListener rpcNotificationListener) {
        getSdlApplicationContext().registerRpcNotificationListener(functionID, rpcNotificationListener);
    }

    @Override
    public final void unregisterRpcNotificationListener(FunctionID functionID, OnRPCNotificationListener rpcNotificationListener) {
        getSdlApplicationContext().unregisterRpcNotificationListener(functionID, rpcNotificationListener);
    }

    @Override
    public final HMICapabilities getHmiCapabilities() {
        return getSdlApplicationContext().getHmiCapabilities();
    }

    @Override
    public final DisplayCapabilities getDisplayCapabilities() {
        return getSdlApplicationContext().getDisplayCapabilities();
    }

    @Override
    public final VehicleType getVehicleType() {
        return getSdlApplicationContext().getVehicleType();
    }

    @Override
    public final SdlMsgVersion getSdlMessageVersion() {
        return getSdlApplicationContext().getSdlMessageVersion();
    }

    @Override
    public final String getModuleVersion() {
        return getSdlApplicationContext().getModuleVersion();
    }

    @Override
    public final Language getConnectedLanguage() {
        return getSdlApplicationContext().getConnectedLanguage();
    }

    @Override
    public DriverDistractionState getCurrentDDState() {
        return getSdlApplicationContext().getCurrentDDState();
    }

    public class SuperNotCalledException extends RuntimeException{

        SuperNotCalledException(String detailMessage){
            super(detailMessage);
        }

    }
}
