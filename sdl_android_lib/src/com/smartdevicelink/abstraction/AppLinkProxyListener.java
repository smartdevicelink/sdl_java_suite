package com.smartdevicelink.abstraction;

import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerABS;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
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
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;

public class AppLinkProxyListener implements IProxyListenerABS{

	private AppLinkAbstraction mAbstraction;

	public AppLinkProxyListener(AppLinkAbstraction appLinkAbstraction) {
		mAbstraction = appLinkAbstraction;
	}

	@Override
	public void onAddCommandResponse(AddCommandResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onAddSubMenuResponse(AddSubMenuResponse arg0) {
		mAbstraction.handleResponse(arg0);	
	}

	@Override
	public void onAlertResponse(AlertResponse arg0) {
		mAbstraction.handleResponse(arg0);	
	}

	@Override
	public void onChangeRegistrationResponse(ChangeRegistrationResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onCreateInteractionChoiceSetResponse(
			CreateInteractionChoiceSetResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onDeleteCommandResponse(DeleteCommandResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onDeleteFileResponse(DeleteFileResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onDeleteInteractionChoiceSetResponse(
			DeleteInteractionChoiceSetResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onError(String arg0, Exception arg1) {
		mAbstraction.onError(arg0, arg1);
	}

	@Override
	public void onGenericResponse(GenericResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onGetDTCsResponse(GetDTCsResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onGetVehicleDataResponse(GetVehicleDataResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onListFilesResponse(ListFilesResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onOnAudioPassThru(OnAudioPassThru arg0) {
		mAbstraction.handleNotification(arg0);
	}

	@Override
	public void onOnButtonEvent(OnButtonEvent arg0) {
		mAbstraction.handleNotification(arg0);
	}

	@Override
	public void onOnButtonPress(OnButtonPress arg0) {
		mAbstraction.handleNotification(arg0);

	}

	@Override
	public void onOnCommand(OnCommand arg0) {
		mAbstraction.handleNotification(arg0);
	}

	@Override
	public void onOnDriverDistraction(OnDriverDistraction arg0) {
		mAbstraction.onDriverDistraction(arg0);
	}

	@Override
	public void onOnHMIStatus(OnHMIStatus arg0) {
		mAbstraction.onHMIStatus(arg0);
	}

	@Override
	public void onOnHashChange(OnHashChange arg0) {
		mAbstraction.onHashChange(arg0);

	}

	@Override
	public void onOnKeyboardInput(OnKeyboardInput arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onOnLanguageChange(OnLanguageChange arg0) {
//		mAbstraction.onLanguageChange(arg0);
	}

	@Override
	public void onOnLockScreenNotification(OnLockScreenStatus arg0) {
		mAbstraction.onOnLockScreenNotification(arg0);
	}

	@Override
	public void onOnPermissionsChange(OnPermissionsChange arg0) {
		mAbstraction.onOnPermissionsChange(arg0);
	}

	@Override
	public void onOnSystemRequest(OnSystemRequest arg0) {
		mAbstraction.handleNotification(arg0);
	}

	@Override
	public void onOnTBTClientState(OnTBTClientState arg0) {
//		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onOnTouchEvent(OnTouchEvent arg0) {
	}

	@Override
	public void onOnVehicleData(OnVehicleData arg0) {
		mAbstraction.handleNotification(arg0);
	}

	@Override
	public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onPerformInteractionResponse(PerformInteractionResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onProxyClosed(String arg0, Exception arg1,SdlDisconnectedReason arg2) {
		mAbstraction.onProxyClosed(arg0, arg1, arg2);
	}

	@Override
	public void onPutFileResponse(PutFileResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onReadDIDResponse(ReadDIDResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onScrollableMessageResponse(ScrollableMessageResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSetAppIconResponse(SetAppIconResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onShowResponse(ShowResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSliderResponse(SliderResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSpeakResponse(SpeakResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSubscribeButtonResponse(SubscribeButtonResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onSystemRequestResponse(SystemRequestResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse arg0) {
		mAbstraction.handleResponse(arg0);
	}

	@Override
	public void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response) {
	}

	@Override
	public void onResumeDataPersistence(Boolean bSuccess) {	
		mAbstraction.onResumeDataPersistenceListener(bSuccess);
	}

	@Override
	public void onOnStreamRPC(OnStreamRPC notification) {
		mAbstraction.handleNotification(notification);
	}

	@Override
	public void onStreamRPCResponse(StreamRPCResponse response) {
		mAbstraction.handleResponse(response);
	}

	@Override
	public void onServiceEnded(OnServiceEnded serviceEnded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onServiceNACKed(OnServiceNACKed serviceNACKed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialNumberResponse(DialNumberResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSendLocationResponse(SendLocationResponse response) {
		// TODO Auto-generated method stub
		
	}

}
