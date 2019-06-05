/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartdevicelink.managers;

import android.util.SparseArray;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.IProxyListener;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonPressResponse;
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
import com.smartdevicelink.proxy.rpc.GetAppServiceDataResponse;
import com.smartdevicelink.proxy.rpc.GetCloudAppPropertiesResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetFileResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered;
import com.smartdevicelink.proxy.rpc.OnAppServiceData;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnRCStatus;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemCapabilityUpdated;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
import com.smartdevicelink.proxy.rpc.PerformAppServiceInteractionResponse;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PublishAppServiceResponse;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetCloudAppPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleDataResponse;
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
import com.smartdevicelink.proxy.rpc.UnregisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;

import java.util.concurrent.CopyOnWriteArrayList;

public class ProxyBridge implements IProxyListener{
	private final Object RPC_LISTENER_LOCK = new Object();
	private SparseArray<CopyOnWriteArrayList<OnRPCListener>> rpcListeners = null;
	private final LifecycleListener lifecycleListener;

	@Override
	public void onProxyOpened() {}

	@Override
	public void onRegisterAppInterfaceResponse(RegisterAppInterfaceResponse response) {
		onRPCReceived(response);
		if(response.getSuccess()){
			lifecycleListener.onProxyConnected();
		}
	}

	@Override
	public void onOnAppInterfaceUnregistered(OnAppInterfaceUnregistered notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onUnregisterAppInterfaceResponse(UnregisterAppInterfaceResponse response) {
		onRPCReceived(response);
	}

	protected interface LifecycleListener{
		void onProxyConnected();
		void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason);
		void onServiceEnded(OnServiceEnded serviceEnded);
		void onServiceNACKed(OnServiceNACKed serviceNACKed);
		void onError(String info, Exception e);
	}

	public ProxyBridge( LifecycleListener lifecycleListener){
		this.lifecycleListener = lifecycleListener;
		rpcListeners = new SparseArray<>();
	}

	public boolean onRPCReceived(final RPCMessage message){
		synchronized(RPC_LISTENER_LOCK){
			final int id = FunctionID.getFunctionId(message.getFunctionName());
			CopyOnWriteArrayList<OnRPCListener> listeners = rpcListeners.get(id);
			if(listeners!=null && listeners.size()>0) {
				for (OnRPCListener listener : listeners) {
					listener.onReceived(message);
				}
				return true;
			}
			return false;
		}
	}

	protected void addRpcListener(FunctionID id, OnRPCListener listener){
		synchronized(RPC_LISTENER_LOCK){
			if (id != null && listener != null) {
				if (rpcListeners.indexOfKey(id.getId()) < 0) {
					rpcListeners.put(id.getId(), new CopyOnWriteArrayList<OnRPCListener>());
				}
				rpcListeners.get(id.getId()).add(listener);
			}
		}
	}

	public boolean removeOnRPCListener(FunctionID id, OnRPCListener listener){
		synchronized(RPC_LISTENER_LOCK){
			if(rpcListeners!= null
					&& id != null
					&& listener != null
					&& rpcListeners.indexOfKey(id.getId()) >= 0){
				return rpcListeners.get(id.getId()).remove(listener);
			}
		}
		return false;
	}

	@Override
	public void onProxyClosed(String info, Exception e, SdlDisconnectedReason reason) {
		lifecycleListener.onProxyClosed(info,e,reason);
	}

	@Override
	public void onServiceEnded(OnServiceEnded serviceEnded) {
		lifecycleListener.onServiceEnded(serviceEnded);

	}

	@Override
	public void onServiceNACKed(OnServiceNACKed serviceNACKed) {
		lifecycleListener.onServiceNACKed(serviceNACKed);

	}
	@Override
	public void onError(String info, Exception e) {
		lifecycleListener.onError(info, e);
	}

	@Override
	public void onOnStreamRPC(OnStreamRPC notification) {
		onRPCReceived(notification);

	}

	@Override
	public void onStreamRPCResponse(StreamRPCResponse response) {
		onRPCReceived(response);
	}

	@Override
	public void onOnHMIStatus(OnHMIStatus notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onGenericResponse(GenericResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnCommand(OnCommand notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onAddCommandResponse(AddCommandResponse response) {
		onRPCReceived(response);
	}

	@Override
	public void onAddSubMenuResponse(AddSubMenuResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onAlertResponse(AlertResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onDeleteCommandResponse(DeleteCommandResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onDeleteSubMenuResponse(DeleteSubMenuResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onPerformInteractionResponse(PerformInteractionResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onShowResponse(ShowResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSpeakResponse(SpeakResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnButtonEvent(OnButtonEvent notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onOnButtonPress(OnButtonPress notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSubscribeButtonResponse(SubscribeButtonResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnPermissionsChange(OnPermissionsChange notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onGetVehicleDataResponse(GetVehicleDataResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnVehicleData(OnVehicleData notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onEndAudioPassThruResponse(EndAudioPassThruResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnAudioPassThru(OnAudioPassThru notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onPutFileResponse(PutFileResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onDeleteFileResponse(DeleteFileResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onListFilesResponse(ListFilesResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSetAppIconResponse(SetAppIconResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onScrollableMessageResponse(ScrollableMessageResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onChangeRegistrationResponse(ChangeRegistrationResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnLanguageChange(OnLanguageChange notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onOnHashChange(OnHashChange notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSliderResponse(SliderResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnDriverDistraction(OnDriverDistraction notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onOnTBTClientState(OnTBTClientState notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onOnSystemRequest(OnSystemRequest notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSystemRequestResponse(SystemRequestResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnKeyboardInput(OnKeyboardInput notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onOnTouchEvent(OnTouchEvent notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onDiagnosticMessageResponse(DiagnosticMessageResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onReadDIDResponse(ReadDIDResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onGetDTCsResponse(GetDTCsResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnLockScreenNotification(OnLockScreenStatus notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onDialNumberResponse(DialNumberResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSendLocationResponse(SendLocationResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onShowConstantTbtResponse(ShowConstantTbtResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onAlertManeuverResponse(AlertManeuverResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onUpdateTurnListResponse(UpdateTurnListResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onServiceDataACK(int dataSize) {

	}

	@Override
	public void onGetWayPointsResponse(GetWayPointsResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSubscribeWayPointsResponse(SubscribeWayPointsResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onUnsubscribeWayPointsResponse(UnsubscribeWayPointsResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnWayPointChange(OnWayPointChange notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onGetSystemCapabilityResponse(GetSystemCapabilityResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onGetInteriorVehicleDataResponse(GetInteriorVehicleDataResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onButtonPressResponse(ButtonPressResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onSetInteriorVehicleDataResponse(SetInteriorVehicleDataResponse response) {
		onRPCReceived(response);

	}

	@Override
	public void onOnInteriorVehicleData(OnInteriorVehicleData notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSendHapticDataResponse(SendHapticDataResponse response) {
		onRPCReceived(response);
	}

	@Override
	public void onOnRCStatus(OnRCStatus notification) {
		onRPCReceived(notification);
	}

	@Override
	public void onSetCloudAppProperties(SetCloudAppPropertiesResponse response) {
		onRPCReceived(response);
	}

	@Override
	public void onGetCloudAppProperties(GetCloudAppPropertiesResponse response) {
		onRPCReceived(response);
	}
@Override
	public void onPublishAppServiceResponse(PublishAppServiceResponse response){
		onRPCReceived(response);
	}

	@Override
	public void onGetAppServiceDataResponse(GetAppServiceDataResponse response){
		onRPCReceived(response);
	}

	@Override
	public void onGetFileResponse(GetFileResponse response){
		onRPCReceived(response);
	}

	@Override
	public void onPerformAppServiceInteractionResponse(PerformAppServiceInteractionResponse response){
		onRPCReceived(response);
	}

	@Override
	public void onOnAppServiceData(OnAppServiceData notification){
		onRPCReceived(notification);
	}

	@Override
	public void onOnSystemCapabilityUpdated(OnSystemCapabilityUpdated notification){
		onRPCReceived(notification);
	}
}
