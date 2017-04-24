package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Reset Global Properties Response is sent, when ResetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ResetGlobalPropertiesResponse extends RPCResponse {

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object
	 */
    public ResetGlobalPropertiesResponse() {
        super(FunctionID.RESET_GLOBAL_PROPERTIES.toString());
    }

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object indicated by the Hashtable
	 * parameter
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ResetGlobalPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}