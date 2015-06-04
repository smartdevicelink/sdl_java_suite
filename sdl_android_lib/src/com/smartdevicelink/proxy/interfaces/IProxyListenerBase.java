package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.rpc.notifications.OnAudioPassThru;
import com.smartdevicelink.rpc.notifications.OnButtonEvent;
import com.smartdevicelink.rpc.notifications.OnButtonPress;
import com.smartdevicelink.rpc.notifications.OnCommand;
import com.smartdevicelink.rpc.notifications.OnDriverDistraction;
import com.smartdevicelink.rpc.notifications.OnHMIStatus;
import com.smartdevicelink.rpc.notifications.OnHashChange;
import com.smartdevicelink.rpc.notifications.OnKeyboardInput;
import com.smartdevicelink.rpc.notifications.OnLanguageChange;
import com.smartdevicelink.rpc.notifications.OnLockScreenStatus;
import com.smartdevicelink.rpc.notifications.OnPermissionsChange;
import com.smartdevicelink.rpc.notifications.OnStreamRpc;
import com.smartdevicelink.rpc.notifications.OnSystemRequest;
import com.smartdevicelink.rpc.notifications.OnTbtClientState;
import com.smartdevicelink.rpc.notifications.OnTouchEvent;
import com.smartdevicelink.rpc.notifications.OnVehicleData;
import com.smartdevicelink.rpc.requests.SystemRequestResponse;
import com.smartdevicelink.rpc.responses.AddCommandResponse;
import com.smartdevicelink.rpc.responses.AddSubMenuResponse;
import com.smartdevicelink.rpc.responses.AlertResponse;
import com.smartdevicelink.rpc.responses.ChangeRegistrationResponse;
import com.smartdevicelink.rpc.responses.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.rpc.responses.DeleteCommandResponse;
import com.smartdevicelink.rpc.responses.DeleteFileResponse;
import com.smartdevicelink.rpc.responses.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.rpc.responses.DeleteSubMenuResponse;
import com.smartdevicelink.rpc.responses.DiagnosticMessageResponse;
import com.smartdevicelink.rpc.responses.DialNumberResponse;
import com.smartdevicelink.rpc.responses.EndAudioPassThruResponse;
import com.smartdevicelink.rpc.responses.GenericResponse;
import com.smartdevicelink.rpc.responses.GetDtcsResponse;
import com.smartdevicelink.rpc.responses.GetVehicleDataResponse;
import com.smartdevicelink.rpc.responses.ListFilesResponse;
import com.smartdevicelink.rpc.responses.PerformAudioPassThruResponse;
import com.smartdevicelink.rpc.responses.PerformInteractionResponse;
import com.smartdevicelink.rpc.responses.PutFileResponse;
import com.smartdevicelink.rpc.responses.ReadDidResponse;
import com.smartdevicelink.rpc.responses.ResetGlobalPropertiesResponse;
import com.smartdevicelink.rpc.responses.ScrollableMessageResponse;
import com.smartdevicelink.rpc.responses.SendLocationResponse;
import com.smartdevicelink.rpc.responses.SetAppIconResponse;
import com.smartdevicelink.rpc.responses.SetDisplayLayoutResponse;
import com.smartdevicelink.rpc.responses.SetGlobalPropertiesResponse;
import com.smartdevicelink.rpc.responses.SetMediaClockTimerResponse;
import com.smartdevicelink.rpc.responses.ShowResponse;
import com.smartdevicelink.rpc.responses.SliderResponse;
import com.smartdevicelink.rpc.responses.SpeakResponse;
import com.smartdevicelink.rpc.responses.StreamRpcResponse;
import com.smartdevicelink.rpc.responses.SubscribeButtonResponse;
import com.smartdevicelink.rpc.responses.SubscribeVehicleDataResponse;
import com.smartdevicelink.rpc.responses.UnsubscribeButtonResponse;
import com.smartdevicelink.rpc.responses.UnsubscribeVehicleDataResponse;


public interface IProxyListenerBase  {

	/**
	 * onOnHMIStatus being called indicates that there has been an HMI Level change,
	 * system context change or audio streaming state change.
	 * 
	 * @param notification - Contains information about the HMI Level,
	 * system context and audio streaming state.
	 */
	public void onOnHMIStatus(OnHMIStatus notification);

	/**
	 * onProxyClosed has different functionality for the different models.
	 * In the non-ALM model this indicates that the proxy has experienced an unrecoverable error.
	 * A new proxy object must be initiated to reestablish connection with SDL.
	 * In the ALM model this indicates that the app is no longer registered with SDL
	 * All resources on SDL (addCommands and ChoiceSets) have been deleted and will have to be
	 * recreated upon the next onReadyForInitialization() call-back. 
	 * 
	 * @param info - Includes information about the reason the proxy has been closed.
	 * @param e - The exception that occurred. 
	 */
	public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason);
	
	public void onOnStreamRPC(OnStreamRpc notification);
	
	public void onStreamRPCResponse(StreamRpcResponse response);
	/**
	 * onProxyError() being called indicates that the SDL Proxy experenced an error.
	 * 
	 * @param info - Includes information about the Exception that occurred.
	 * @param e - The exception that occurred. 
	 */
	public void onError(String info, Exception e);
	
	
	/**
	 * onGenericResponse() being called indicates that SDL could not determine the
	 * type of request it is responding to. This is usually result of an unknown RPC Request
	 * being sent.
	 * 
	 * @param response - Includes detailed information about the response.
	 */
	public void onGenericResponse(GenericResponse response);
	
	/**
	 * onOnCommand() being called indicates that the user selected a command on SDL.
	 * 
	 * @param notification - Contains information about the command chosen.
	 */
	public void onOnCommand(OnCommand notification);
	
	/**
	 * onAddCommandResponse() being called indicates that SDL has responded to
	 * a request to add a command.
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onAddCommandResponse(AddCommandResponse response);
	
	/**
	 * onAddSubMenuResponse() being called indicates that SDL has responded to
	 * a request to add a command.
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onAddSubMenuResponse(AddSubMenuResponse response);
	
	/**
	 * onCreateInteractionChoiceSetResponse() being called indicates that SDL has
	 * responded to a request to add an interactionChoiceSet.
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response);
	
	/**
	 * onAlertResponse being called indicates that SDL has
	 * responded to a request to alert the user. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onAlertResponse(AlertResponse response);
	
	/**
	 * onDeleteCommandResponse being called indicates that SDL has
	 * responded to a request to delete a command. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onDeleteCommandResponse(DeleteCommandResponse response);
	
	/**
	 * onDeleteCommandResponse being called indicates that SDL has
	 * responded to a request to delete an interaction choice set. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response);
	
	/**
	 * onDeleteCommandResponse being called indicates that SDL has
	 * responded to a request to delete a submenu. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse response);
	
	/**
	 * onPerformInteractionResponse being called indicates that SDL has
	 * responded to a request to perform an interaction. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onPerformInteractionResponse(PerformInteractionResponse response);
	
	/**
	 * onResetGlobalPropertiesResponse being called indicates that SDL has
	 * responded to a request to reset global properties. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse response);
	
	/**
	 * onSetGlobalPropertiesResponse being called indicates that SDL has
	 * responded to a request to set global properties. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response);
	
	/**
	 * onSetMediaClockTimerResponse being called indicates that SDL has
	 * responded to a request to set the media clock timer. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response);
	
	/**
	 * onShowResponse being called indicates that SDL has
	 * responded to a request to display information to the user. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onShowResponse(ShowResponse response);
	
	/**
	 * onSpeakResponse being called indicates that SDL has
	 * responded to a request to speak information to the user. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onSpeakResponse(SpeakResponse response);
	
	/**
	 * onButtonEvent being called indicates that a button event has occurred. 
	 * 
	 * @param notification - Contains information about the notification sent from SDL.
	 */
	public void onOnButtonEvent(OnButtonEvent notification);

	/**
	 * onButtonPress being called indicates that SDL has a button has 
	 * been pressed by the user. 
	 * 
	 * @param notification - Contains information about the notification sent from SDL.
	 */
	public void onOnButtonPress(OnButtonPress notification);
	
	/**
	 * onSubscribeButtonResponse being called indicates that SDL has
	 * responded to a request to subscribe to button events and button presses. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onSubscribeButtonResponse(SubscribeButtonResponse response);
	
	/**
	 * onUnsubscribeButtonResponse being called indicates that SDL has
	 * responded to a request to unsubscribe from button events and button presses. 
	 * 
	 * @param response - Contains information about the response sent from SDL.
	 */
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response);

	/**
	 * onOnPermissionsChange being called indicates that your app permissions have 
	 * changed due to a policy table change. This can mean your app has received additional
	 * permissions OR lost permissions.
	 * 
	 * @param notification - Contains information about the changed permissions.
	 */
	public void onOnPermissionsChange(OnPermissionsChange notification);
	
	public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response);
	
	public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse response);
	
	public void onGetVehicleDataResponse(GetVehicleDataResponse response);				
	
	public void onOnVehicleData(OnVehicleData notification);
	
	public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response);
	
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse response);
	
	public void onOnAudioPassThru(OnAudioPassThru notification);

	public void onPutFileResponse(PutFileResponse response);
	
	public void onDeleteFileResponse(DeleteFileResponse response);
	
	public void onListFilesResponse(ListFilesResponse response);

	public void onSetAppIconResponse(SetAppIconResponse response);
	
	public void onScrollableMessageResponse(ScrollableMessageResponse response);

	public void onChangeRegistrationResponse(ChangeRegistrationResponse response);

	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response);
	
	public void onOnLanguageChange(OnLanguageChange notification);
	
	public void onOnHashChange(OnHashChange notification);
	
	public void onSliderResponse(SliderResponse response);
	
	public void onOnDriverDistraction(OnDriverDistraction notification);
		
	public void onOnTBTClientState(OnTbtClientState notification);
	
	public void onOnSystemRequest(OnSystemRequest notification);
	
	public void onSystemRequestResponse(SystemRequestResponse response);
	
	public void onOnKeyboardInput(OnKeyboardInput notification);
	
	public void onOnTouchEvent(OnTouchEvent notification);
	
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response);
	
	public void onReadDIDResponse(ReadDidResponse response);
	
	public void onGetDTCsResponse(GetDtcsResponse response);
	
	public void onOnLockScreenNotification(OnLockScreenStatus notification);
	
	public void onDialNumberResponse(DialNumberResponse response);
	
	public void onSendLocationResponse(SendLocationResponse response);
}
