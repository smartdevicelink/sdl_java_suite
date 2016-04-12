package com.smartdevicelink.api;

import android.content.Context;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.ArrayList;

public class SdlApplication extends SdlContextAbsImpl implements IProxyListenerALM{

    private static final String TAG = SdlApplication.class.getSimpleName();

    public static final int BACK_BUTTON_ID = 1010;

    public enum Status {
        CONNECTING,
        CONNECTED,
        DISCONNECTED
    }

    private int coorId = 1001;

    private SdlApplicationConfig mApplicationConfig;

    private SdlActivityManager mSdlActivityManager;
    private SdlProxyALM mSdlProxyALM;

    private final ArrayList<LifecycleListener> mLifecycleListeners = new ArrayList<>();

    private ConnectionStatusListener mApplicationStatusListener;
    private Status mConnectionStatus;

    private boolean isFirstHmiReceived = false;
    private boolean isFirstHmiNotNoneReceived = false;
    
    SdlApplication(SdlConnectionService service, SdlApplicationConfig config, ConnectionStatusListener listener){
        initialize(service.getApplicationContext());
        mApplicationConfig = config;
        mApplicationStatusListener = listener;
        mSdlActivityManager = new SdlActivityManager();
        mLifecycleListeners.add(mSdlActivityManager);
        mSdlProxyALM = mApplicationConfig.buildProxy(service, null, this);
        if(mSdlProxyALM != null){
            mConnectionStatus = Status.CONNECTING;
            listener.onStatusChange(mApplicationConfig.getAppId(), Status.CONNECTING);
        }
    }

    void initialize(Context androidContext){
        if(!isInitialized()) {
            setAndroidContext(androidContext);
            setSdlApplicationContext(this);
            setInitialized(true);
        } else {
            Log.w(TAG, "Attempting to initialize SdlContext that is already initialized. Class " +
                    this.getClass().getCanonicalName());
        }
    }

    final public boolean sendRpc(RPCRequest request){
        if(mSdlProxyALM != null){
            try {
                request.setCorrelationID(coorId++);
                Log.d(TAG, "Sending RPCRequest type " + request.getFunctionName());
                mSdlProxyALM.sendRPCRequest(request);
            } catch (SdlException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    final public void addNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener){
        mSdlProxyALM.addOnRPCNotificationListener(notificationId, listener);
    }

    final public void removeNotificationListener(FunctionID notificationId){
        mSdlProxyALM.removeOnRPCNotificationListener(notificationId);
    }

    final public String getName(){
        return mApplicationConfig.getAppName();
    }

    final void closeConnection(boolean notifyStatusListener) {
        if(mConnectionStatus != Status.DISCONNECTED) {
            for(LifecycleListener listener: mLifecycleListeners){
                listener.onSdlDisconnect();
            }
            mConnectionStatus = Status.DISCONNECTED;
            if(notifyStatusListener)
                mApplicationStatusListener.onStatusChange(mApplicationConfig.getAppId(), Status.DISCONNECTED);
            try {
                mSdlProxyALM.dispose();
            } catch (SdlException e) {
                e.printStackTrace();
            }
            mSdlProxyALM = null;
        }
    }

    @Override
    public String toString(){
        return String.format("SdlApplication: %s-%s",
                mApplicationConfig.getAppName(), mApplicationConfig.getAppId());
    }

    /****************************
     SdlContext interface methods
     ****************************/

    @Override
    public final void startSdlActivity(Class<? extends SdlActivity> activity, int flags) {
        mSdlActivityManager.startSdlActivity(this, activity, flags);
    }

    /***********************************
     IProxyListenerALM interface methods
     ***********************************/

    @Override
    public final void onOnHMIStatus(OnHMIStatus notification) {

        if(notification == null || notification.getHmiLevel() == null){
            Log.w(TAG, "INVALID OnHMIStatus Notification Received!");
            return;
        }

        HMILevel hmiLevel = notification.getHmiLevel();

        Log.i(TAG, toString() + " Received HMILevel: " + hmiLevel.name());

        if(!isFirstHmiReceived){
            isFirstHmiReceived = true;
            mConnectionStatus = Status.CONNECTED;
            mApplicationStatusListener.onStatusChange(mApplicationConfig.getAppId(), Status.CONNECTED);

            for(LifecycleListener listener: mLifecycleListeners){
                listener.onSdlConnect();
            }
        }

        if(!isFirstHmiNotNoneReceived && hmiLevel != HMILevel.HMI_NONE){
            Log.i(TAG, toString() + " is launching activity: " + mApplicationConfig.getMainSdlActivity().getCanonicalName());
            // TODO: Add check for resume
            mSdlActivityManager.onSdlAppLaunch(this, mApplicationConfig.getMainSdlActivity());
            isFirstHmiNotNoneReceived = true;
        }

        switch (hmiLevel){

            case HMI_FULL:
                for(LifecycleListener listener: mLifecycleListeners){
                    listener.onForeground();
                }
                break;
            case HMI_LIMITED:
            case HMI_BACKGROUND:
                for(LifecycleListener listener: mLifecycleListeners){
                    listener.onBackground();
                }
                break;
            case HMI_NONE:
                if(isFirstHmiNotNoneReceived) {
                    isFirstHmiNotNoneReceived = false;
                    for(LifecycleListener listener: mLifecycleListeners){
                        listener.onExit();
                    }
                }
                break;
        }

    }

    @Override
    public final void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
        closeConnection(true);
    }

    @Override
    public final void onServiceEnded(OnServiceEnded serviceEnded) {

    }

    @Override
    public final void onServiceNACKed(OnServiceNACKed serviceNACKed) {

    }

    @Override
    public final void onOnStreamRPC(OnStreamRPC notification) {

    }

    @Override
    public final void onStreamRPCResponse(StreamRPCResponse response) {

    }

    @Override
    public final void onError(String info, Exception e) {
        closeConnection(true);
    }

    @Override
    public final void onGenericResponse(GenericResponse response) {

    }

    @Override
    public final void onOnCommand(OnCommand notification) {

    }

    @Override
    public final void onAddCommandResponse(AddCommandResponse response) {

    }

    @Override
    public final void onAddSubMenuResponse(AddSubMenuResponse response) {

    }

    @Override
    public final void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {

    }

    @Override
    public final void onAlertResponse(AlertResponse response) {

    }

    @Override
    public final void onDeleteCommandResponse(DeleteCommandResponse response) {

    }

    @Override
    public final void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response) {

    }

    @Override
    public final void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {

    }

    @Override
    public final void onPerformInteractionResponse(PerformInteractionResponse response) {

    }

    @Override
    public final void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse response) {

    }

    @Override
    public final void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response) {

    }

    @Override
    public final void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {

    }

    @Override
    public final void onShowResponse(ShowResponse response) {

    }

    @Override
    public final void onSpeakResponse(SpeakResponse response) {

    }

    @Override
    public final void onOnButtonEvent(OnButtonEvent notification) {

    }

    @Override
    public final void onOnButtonPress(OnButtonPress notification) {
        if(notification.getCustomButtonName() != null && notification.getCustomButtonName() == BACK_BUTTON_ID){
            mSdlActivityManager.back();
        }
    }

    @Override
    public final void onSubscribeButtonResponse(SubscribeButtonResponse response) {

    }

    @Override
    public final void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {

    }

    @Override
    public final void onOnPermissionsChange(OnPermissionsChange notification) {

    }

    @Override
    public final void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {

    }

    @Override
    public final void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse response) {

    }

    @Override
    public final void onGetVehicleDataResponse(GetVehicleDataResponse response) {

    }

    @Override
    public final void onOnVehicleData(OnVehicleData notification) {

    }

    @Override
    public final void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {

    }

    @Override
    public final void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {

    }

    @Override
    public final void onOnAudioPassThru(OnAudioPassThru notification) {

    }

    @Override
    public final void onPutFileResponse(PutFileResponse response) {

    }

    @Override
    public final void onDeleteFileResponse(DeleteFileResponse response) {

    }

    @Override
    public final void onListFilesResponse(ListFilesResponse response) {

    }

    @Override
    public final void onSetAppIconResponse(SetAppIconResponse response) {

    }

    @Override
    public final void onScrollableMessageResponse(ScrollableMessageResponse response) {

    }

    @Override
    public final void onChangeRegistrationResponse(ChangeRegistrationResponse response) {

    }

    @Override
    public final void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {

    }

    @Override
    public final void onOnLanguageChange(OnLanguageChange notification) {

    }

    @Override
    public final void onOnHashChange(OnHashChange notification) {

    }

    @Override
    public final void onSliderResponse(SliderResponse response) {

    }

    @Override
    public final void onOnDriverDistraction(OnDriverDistraction notification) {

    }

    @Override
    public final void onOnTBTClientState(OnTBTClientState notification) {

    }

    @Override
    public final void onOnSystemRequest(OnSystemRequest notification) {

    }

    @Override
    public final void onSystemRequestResponse(SystemRequestResponse response) {

    }

    @Override
    public final void onOnKeyboardInput(OnKeyboardInput notification) {

    }

    @Override
    public final void onOnTouchEvent(OnTouchEvent notification) {

    }

    @Override
    public final void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {

    }

    @Override
    public final void onReadDIDResponse(ReadDIDResponse response) {

    }

    @Override
    public final void onGetDTCsResponse(GetDTCsResponse response) {

    }

    @Override
    public final void onOnLockScreenNotification(OnLockScreenStatus notification) {

    }

    @Override
    public final void onDialNumberResponse(DialNumberResponse response) {

    }

    @Override
    public final void onSendLocationResponse(SendLocationResponse response) {

    }

    @Override
    public final void onShowConstantTbtResponse(ShowConstantTbtResponse response) {

    }

    @Override
    public final void onAlertManeuverResponse(AlertManeuverResponse response) {

    }

    @Override
    public final void onUpdateTurnListResponse(UpdateTurnListResponse response) {

    }

    @Override
    public final void onServiceDataACK() {

    }

    interface ConnectionStatusListener {

        void onStatusChange(String appId, SdlApplication.Status status);

    }

    interface LifecycleListener {

        void onSdlConnect();
        void onBackground();
        void onForeground();
        void onExit();
        void onSdlDisconnect();

    }

}
