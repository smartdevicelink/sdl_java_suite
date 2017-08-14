package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.HashMap;

public class SystemCapabilityManager {
	HashMap<SystemCapabilityType, Object> cachedSystemCapabilities = new HashMap<>();
	ISystemCapabilityManager callback;

	interface ISystemCapabilityManager{
		void onSendPacketRequest(RPCRequest message);
	}

	public SystemCapabilityManager(ISystemCapabilityManager callback){
		this.callback = callback;
	}

	public void parseRAIResponse(RegisterAppInterfaceResponse response){
		cachedSystemCapabilities.put(SystemCapabilityType.HMI, response.getHmiCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.DISPLAY, response.getDisplayCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.AUDIO_PASSTHROUGH, response.getAudioPassThruCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.BUTTON, response.getButtonCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.HMI_ZONE, response.getHmiZoneCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.PRESET_BANK, response.getPresetBankCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.SOFTBUTTON, response.getSoftButtonCapabilities());
		cachedSystemCapabilities.put(SystemCapabilityType.SPEECH, response.getSpeechCapabilities());
	}

	/**
	 * @param systemCapabilityType Type of capability desired
	 * @param scListener callback for
	 * @return
	 */
	public Object getSystemCapability(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener){
		Object capability = cachedSystemCapabilities.get(systemCapabilityType);
		if(capability != null){
			scListener.onCapabilityRetrieved(capability);
			return capability;
		}else if(scListener == null){
			return null;
		}

		capability = getSystemCapabilityCommon(systemCapabilityType);
		scListener.onCapabilityRetrieved(capability);
		return capability;
	}

	public Object getSystemCapability(final SystemCapabilityType systemCapabilityType){
		Object capability = cachedSystemCapabilities.get(systemCapabilityType);
		if(capability != null){
			return capability;
		}

		return getSystemCapabilityCommon(systemCapabilityType);
	}

	/**
	 * @param systemCapabilityType Type of capability desired
	 * @return Synchronous result returned by GetSystemCapabilityResponse, null if systemCapabilityType is null
	 */
	private Object getSystemCapabilityCommon(final SystemCapabilityType systemCapabilityType){

		final Object RETURN_LOCK = new Object();

		GetSystemCapability request = new GetSystemCapability();
		request.setSystemCapabilityType(systemCapabilityType);
		request.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					Object retrievedCapability = ((GetSystemCapabilityResponse) response).getSystemCapability().getCapabilityForType(systemCapabilityType);
					cachedSystemCapabilities.put(systemCapabilityType, retrievedCapability);
				}
				RETURN_LOCK.notify();
			}
		});

		callback.onSendPacketRequest(request);

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					RETURN_LOCK.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		return cachedSystemCapabilities.get(systemCapabilityType);
	}
}
