package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Set Global Properties Response is sent, when SetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetGlobalPropertiesResponse extends RPCResponse {

	/**
	 * Constructs a new SetGlobalPropertiesResponse object
	 */
    public SetGlobalPropertiesResponse() {
        super(FunctionID.SET_GLOBAL_PROPERTIES.toString());
    }

	/**
	 * Constructs a new SetGlobalPropertiesResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetGlobalPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}