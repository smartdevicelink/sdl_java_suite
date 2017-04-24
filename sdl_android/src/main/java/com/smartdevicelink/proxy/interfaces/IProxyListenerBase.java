package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
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
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
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
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
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
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;


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

	public void onServiceEnded(OnServiceEnded serviceEnded);

	public void onServiceNACKed(OnServiceNACKed serviceNACKed);

	public void onOnStreamRPC(OnStreamRPC notification);
	
	public void onStreamRPCResponse(StreamRPCResponse response);

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
		
	public void onOnTBTClientState(OnTBTClientState notification);
	
	public void onOnSystemRequest(OnSystemRequest notification);
	
	public void onSystemRequestResponse(SystemRequestResponse response);
	
	public void onOnKeyboardInput(OnKeyboardInput notification);
	
	public void onOnTouchEvent(OnTouchEvent notification);
	
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response);
	
	public void onReadDIDResponse(ReadDIDResponse response);
	
	public void onGetDTCsResponse(GetDTCsResponse response);
	
	public void onOnLockScreenNotification(OnLockScreenStatus notification);
	
	public void onDialNumberResponse(DialNumberResponse response);
	
	public void onSendLocationResponse(SendLocationResponse response);

	public void onShowConstantTbtResponse(ShowConstantTbtResponse response);

	public void onAlertManeuverResponse(AlertManeuverResponse response);

	public void onUpdateTurnListResponse(UpdateTurnListResponse response);

	public void onServiceDataACK(int dataSize);
	public void onGetWayPointsResponse(GetWayPointsResponse response);

	public void onSubscribeWayPointsResponse(SubscribeWayPointsResponse response);

	public void onUnsubscribeWayPointsResponse(UnsubscribeWayPointsResponse response);
	public void onOnWayPointChange(OnWayPointChange notification);
}
