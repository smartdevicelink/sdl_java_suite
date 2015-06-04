package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Reset Global Properties Response is sent, when ResetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ResetGlobalPropertiesResponse extends RpcResponse {

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object
	 */
    public ResetGlobalPropertiesResponse() {
        super(FunctionId.RESET_GLOBAL_PROPERTIES.toString());
    }

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ResetGlobalPropertiesResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}