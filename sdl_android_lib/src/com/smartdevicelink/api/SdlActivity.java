package com.smartdevicelink.api;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.interfaces.SdlButtonListener;
import com.smartdevicelink.api.interfaces.SdlContext;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.api.menu.SdlMenuManager;
import com.smartdevicelink.api.menu.SdlMenuOption;
import com.smartdevicelink.api.menu.SdlMenuTransaction;
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

    final void initialize(SdlContext sdlContext) {
        if (!isInitialized()) {
            setSdlApplicationContext(sdlContext);
            setAndroidContext(sdlContext.getAndroidApplicationContext());
            setInitialized(true);
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

    @CallSuper
    protected void onCreate(@Nullable Bundle bundle) {
        superCalled = true;
    }

    @CallSuper
    protected void onRestart() {
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
    }

    final void performRestart() {
        superCalled = false;
        mActivityState = SdlActivityState.POST_CREATE;
        this.onRestart();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onRestart(). This should NEVER happen.");
    }

    final void performStart() {
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        getSdlMenuManager().redoTransactions(this);
        this.onStart();
        Log.d(TAG, "Redo complete.");
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStart(). This should NEVER happen.");
    }

    final void performForeground() {
        superCalled = false;
        mActivityState = SdlActivityState.FOREGROUND;
        this.onForeground();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onForeground(). This should NEVER happen.");
    }

    final void performBackground() {
        superCalled = false;
        mActivityState = SdlActivityState.BACKGROUND;
        this.onBackground();
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onBackground(). This should NEVER happen.");
    }

    final void performStop() {
        superCalled = false;
        mActivityState = SdlActivityState.STOPPED;
        this.onStop();
        getSdlMenuManager().undoTransactions(this);
        if (!superCalled) throw new SuperNotCalledException(this.getClass().getCanonicalName()
                + " did not call through to super() in method onStop(). This should NEVER happen.");
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
    public final int registerButtonCallback(SdlButtonListener listener) {
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
    public SdlPermissionManager getSdlPermissionManager() {
        return getSdlApplicationContext().getSdlPermissionManager();
    }

    public final SdlFileManager getSdlFileManager() {
        return getSdlApplicationContext().getSdlFileManager();
    }

    @Override
    public final void registerMenuCallback(int id, SdlMenuOption.SelectListener listener) {
        getSdlApplicationContext().registerMenuCallback(id, listener);
    }

    @Override
    public final void unregisterMenuCallback(int id) {
        getSdlApplicationContext().unregisterMenuCallback(id);
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
