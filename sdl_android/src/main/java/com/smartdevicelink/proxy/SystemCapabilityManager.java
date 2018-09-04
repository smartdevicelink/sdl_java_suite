package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.GetSystemCapability;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SystemCapabilityManager {
	private final HashMap<SystemCapabilityType, Object> cachedSystemCapabilities;
	private final HashMap<SystemCapabilityType, CopyOnWriteArrayList<OnSystemCapabilityListener>> onSystemCapabilityListeners;
	private final Object LISTENER_LOCK;
	private final ISdl callback;

	public SystemCapabilityManager(ISdl callback){
		this.callback = callback;
		this.LISTENER_LOCK = new Object();
		this.onSystemCapabilityListeners = new HashMap<>();
		this.cachedSystemCapabilities = new HashMap<>();
	}

	public void parseRAIResponse(RegisterAppInterfaceResponse response){
		if(response!=null && response.getSuccess()) {
			setCapability(SystemCapabilityType.HMI, response.getHmiCapabilities());
			setCapability(SystemCapabilityType.DISPLAY, response.getDisplayCapabilities());
			setCapability(SystemCapabilityType.AUDIO_PASSTHROUGH, response.getAudioPassThruCapabilities());
			setCapability(SystemCapabilityType.PCM_STREAMING, response.getPcmStreamingCapabilities());
			setCapability(SystemCapabilityType.BUTTON, response.getButtonCapabilities());
			setCapability(SystemCapabilityType.HMI_ZONE, response.getHmiZoneCapabilities());
			setCapability(SystemCapabilityType.PRESET_BANK, response.getPresetBankCapabilities());
			setCapability(SystemCapabilityType.SOFTBUTTON, response.getSoftButtonCapabilities());
			setCapability(SystemCapabilityType.SPEECH, response.getSpeechCapabilities());
			setCapability(SystemCapabilityType.VOICE_RECOGNITION, response.getVrCapabilities());
		}
	}

	/**
	 * Sets a capability in the cached map. This should only be done when an RPC is received and contains updates to the capability
	 * that is being cached in the SystemCapabilityManager.
	 * @param systemCapabilityType
	 * @param capability
	 */
	public synchronized void setCapability(SystemCapabilityType systemCapabilityType, Object capability){
			cachedSystemCapabilities.put(systemCapabilityType, capability);
			notifyListeners(systemCapabilityType, capability);
	}

	/**
	 * Notify listners in the list about the new retrieved capability
	 * @param systemCapabilityType
	 * @param capability
	 */
	private void notifyListeners(SystemCapabilityType systemCapabilityType, Object capability) {
		synchronized(LISTENER_LOCK){
			CopyOnWriteArrayList<OnSystemCapabilityListener> listeners = onSystemCapabilityListeners.get(systemCapabilityType);
			if(listeners != null && listeners.size() > 0) {
				for (OnSystemCapabilityListener listener : listeners) {
					listener.onCapabilityRetrieved(capability);
				}
			}
		}
	}

	/**
	 * Ability to see if the connected module supports the given capability. Useful to check before
	 * attempting to query for capabilities that require asynchronous calls to initialize.
	 * @param type the SystemCapabilityType that is to be checked
	 * @return if that capability is supported with the current, connected module
	 */
	public boolean isCapabilitySupported(SystemCapabilityType type){
		if(cachedSystemCapabilities.get(type) != null){
			//The capability exists in the map and is not null
			return true;
		}else if(cachedSystemCapabilities.containsKey(SystemCapabilityType.HMI)){
			HMICapabilities hmiCapabilities = ((HMICapabilities)cachedSystemCapabilities.get(SystemCapabilityType.HMI));
			switch (type) {
				case NAVIGATION:
					return hmiCapabilities.isNavigationAvailable();
				case PHONE_CALL:
					return hmiCapabilities.isPhoneCallAvailable();
				case VIDEO_STREAMING:
					return hmiCapabilities.isVideoStreamingAvailable();
				case REMOTE_CONTROL:
					return hmiCapabilities.isRemoteControlAvailable();
				default:
					return false;
			}
		}else{
			return false;
		}
	}
	/**
	 * @param systemCapabilityType Type of capability desired
	 * @param scListener callback to execute upon retrieving capability
	 */
	public void getCapability(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener){
		Object capability = cachedSystemCapabilities.get(systemCapabilityType);
		if(capability != null){
			scListener.onCapabilityRetrieved(capability);
			return;
		}else if(scListener == null){
			return;
		}

		retrieveCapability(systemCapabilityType, scListener);
	}

	/**
	 * @param systemCapabilityType Type of capability desired
	 * @return Desired capability if it is cached in the manager, otherwise returns a null object
	 * and works in the background to retrieve the capability for the next call
	 */
	public Object getCapability(final SystemCapabilityType systemCapabilityType){
		Object capability = cachedSystemCapabilities.get(systemCapabilityType);
		if(capability != null){
			return capability;
		}

		retrieveCapability(systemCapabilityType, null);
		return null;
	}

	/**
	 * Add a listener to be called whenever a new capability is retrieved
	 * @param systemCapabilityType Type of capability desired
	 * @param listener callback to execute upon retrieving capability
	 */
	public void addOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener){
		getCapability(systemCapabilityType, listener);
		synchronized(LISTENER_LOCK){
			if (systemCapabilityType != null && listener != null) {
				if (onSystemCapabilityListeners.get(systemCapabilityType) == null) {
					onSystemCapabilityListeners.put(systemCapabilityType, new CopyOnWriteArrayList<OnSystemCapabilityListener>());
				}
				onSystemCapabilityListeners.get(systemCapabilityType).add(listener);
			}
		}
	}

	/**
	 * Remove an OnSystemCapabilityListener that was previously added
	 * @param systemCapabilityType Type of capability
	 * @param listener the listener that should be removed
	 */
	public boolean removeOnSystemCapabilityListener(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener listener){
		synchronized(LISTENER_LOCK){
			if(onSystemCapabilityListeners!= null
					&& systemCapabilityType != null
					&& listener != null
					&& onSystemCapabilityListeners.get(systemCapabilityType) != null){
				return onSystemCapabilityListeners.get(systemCapabilityType).remove(listener);
			}
		}
		return false;
	}

	/**
	 * @param systemCapabilityType Type of capability desired
	 * passes GetSystemCapabilityType request to  `callback` to be sent by proxy
	 */
	private void retrieveCapability(final SystemCapabilityType systemCapabilityType, final OnSystemCapabilityListener scListener){
		final GetSystemCapability request = new GetSystemCapability();
		request.setSystemCapabilityType(systemCapabilityType);
		request.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					Object retrievedCapability = ((GetSystemCapabilityResponse) response).getSystemCapability().getCapabilityForType(systemCapabilityType);
					cachedSystemCapabilities.put(systemCapabilityType, retrievedCapability);
					if(scListener!=null){scListener.onCapabilityRetrieved(retrievedCapability);	}
				}else{
					if(scListener!=null){scListener.onError(response.getInfo());}
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				if(scListener!=null){scListener.onError(info);}
			}
		});
		request.setCorrelationID(CorrelationIdGenerator.generateId());

		if(callback!=null){
			callback.sendRPCRequest(request);
		}
	}

	/**
	 * Converts a capability object into a list.
	 * @param object the capability that needs to be converted
	 * @param classType The class type of that should be contained in the list
	 * @return a List of capabilities if object is instance of List, otherwise it will return null.
	 */
	@SuppressWarnings({"unchecked"})
	public static <T> List<T> convertToList(Object object, Class<T> classType){
		if(classType!=null && object!=null && object instanceof List ){
			List list = (List)object;
			if(!list.isEmpty()){
				if(classType.isInstance(list.get(0))){
					return (List<T>)object;
				}else{
					//The list is not of the correct list type
					return null;
				}
			}else {
				//We return a new list of type T instead of null because while we don't know if
				//the original list was of type T we want to ensure that we don't throw a cast class exception
				//but still
				return new ArrayList<T>();
			}
		}else{
			return null;
		}
	}
}
