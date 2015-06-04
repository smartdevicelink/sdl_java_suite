package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Unsubscribe Button Response is sent, when UnsubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnsubscribeButtonResponse extends RpcResponse {

	/**
	 * Constructs a new UnsubscribeButtonResponse object
	 */
    public UnsubscribeButtonResponse() {
        super(FunctionId.UNSUBSCRIBE_BUTTON.toString());
    }

	/**
	 * Constructs a new UnsubscribeButtonResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeButtonResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}