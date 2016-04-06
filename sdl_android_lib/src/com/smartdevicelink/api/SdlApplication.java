package com.smartdevicelink.api;

import android.content.Context;

import com.smartdevicelink.exception.SdlException;
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
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

public class SdlApplication implements SdlContext, IProxyListenerALM{

    public enum Status {
        CONNECTING,
        CONNECTED,
        DISCONNECTED
    }

    private SdlProxyALM mSdlProxyALM;
    private SdlAppStatusListener mApplicationStatusListener;
    private SdlApplicationConfig mApplicationConfig;
    private Status mConnectionStatus;
    private Context mAndroidContext;

    private boolean firstHmiReceived = false;
    
    SdlApplication(SdlConnectionService service, SdlApplicationConfig config, SdlAppStatusListener listener){
        mApplicationConfig = config;
        mApplicationStatusListener = listener;
        mAndroidContext = service.getApplicationContext();
        mSdlProxyALM = mApplicationConfig.buildProxy(service, null, this);
        if(mSdlProxyALM != null){
            mConnectionStatus = Status.CONNECTING;
            listener.onStatusChange(mApplicationConfig.getAppId(), Status.CONNECTING);
        }
    }

    boolean sendRpc(RPCRequest request){
        if(mSdlProxyALM != null){
            try {
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

    void closeApplication(boolean notifyStatusListener) {
        if(mConnectionStatus != Status.DISCONNECTED) {
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

    /***********************************
     IProxyListenerALM interface methods
     ***********************************/

    @Override
    public final void onOnHMIStatus(OnHMIStatus notification) {
        if(!firstHmiReceived){
            firstHmiReceived = true;
            mConnectionStatus = Status.CONNECTED;
            mApplicationStatusListener.onStatusChange(mApplicationConfig.getAppId(), Status.CONNECTED);
        }
    }

    @Override
    public final void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
        closeApplication(true);
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
        closeApplication(true);
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
}
