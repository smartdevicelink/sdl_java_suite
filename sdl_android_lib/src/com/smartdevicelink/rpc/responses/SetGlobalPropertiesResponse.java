package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Set Global Properties Response is sent, when SetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetGlobalPropertiesResponse extends RpcResponse {

	/**
	 * Constructs a new SetGlobalPropertiesResponse object
	 */
    public SetGlobalPropertiesResponse() {
        super(FunctionId.SET_GLOBAL_PROPERTIES.toString());
    }

	/**
	 * Constructs a new SetGlobalPropertiesResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetGlobalPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}