package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Sub scribeButton Response is sent, when SubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new SubscribeButtonResponse object
	 */
    public SubscribeButtonResponse() {
        super(FunctionId.SUBSCRIBE_BUTTON.toString());
    }

	/**
	 * Constructs a new SubscribeButtonResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SubscribeButtonResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}