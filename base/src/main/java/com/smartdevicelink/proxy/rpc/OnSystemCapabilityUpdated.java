package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * A notification to inform the connected device that a specific system capability has changed.
 */
public class OnSystemCapabilityUpdated extends RPCNotification {

	public static final String KEY_SYSTEM_CAPABILITY = "systemCapability";

	// Constructors

	public OnSystemCapabilityUpdated() {
		super(FunctionID.ON_SYSTEM_CAPABILITY_UPDATED.toString());
	}

	public OnSystemCapabilityUpdated(Hashtable<String, Object> hash) {
		super(hash);
	}

	public OnSystemCapabilityUpdated(@NonNull SystemCapability serviceData) {
		this();
		setSystemCapability(serviceData);
	}

	// Setters and Getters

	/**
	 * @param systemCapability - The system capability that has been updated
	 */
	public void setSystemCapability(@NonNull SystemCapability systemCapability){
		setParameters(KEY_SYSTEM_CAPABILITY, systemCapability);
	}

	/**
	 * @return systemCapability - The system capability that has been updated
	 */
	public SystemCapability getSystemCapability(){
		return (SystemCapability) getObject(SystemCapability.class, KEY_SYSTEM_CAPABILITY);
	}

}
