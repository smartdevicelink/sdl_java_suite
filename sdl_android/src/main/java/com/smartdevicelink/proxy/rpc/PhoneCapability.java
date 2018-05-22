package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Extended capabilities of the module's phone feature
 */

public class PhoneCapability extends RPCStruct {
	public static final String KEY_DIALNUMBER_ENABLED = "dialNumberEnabled";

	public PhoneCapability(){}

	public PhoneCapability(Hashtable<String, Object> hash) {
		super(hash);
	}

	public Boolean getDialNumberEnabled(){
		return getBoolean(KEY_DIALNUMBER_ENABLED);
	}

	public void setDialNumberEnabled(Boolean dialNumberEnabled){
		setValue(KEY_DIALNUMBER_ENABLED, dialNumberEnabled);
	}
}
