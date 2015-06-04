package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Show Response is sent, when Show has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ShowResponse extends RpcResponse {

	/**
	 * Constructs a new ShowResponse object
	 */
    public ShowResponse() {
        super(FunctionId.SHOW.toString());
    }

	/**
	 * Constructs a new ShowResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ShowResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}