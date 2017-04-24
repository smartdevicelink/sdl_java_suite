package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Unsubscribe Button Response is sent, when UnsubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnsubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new UnsubscribeButtonResponse object
	 */
    public UnsubscribeButtonResponse() {
        super(FunctionID.UNSUBSCRIBE_BUTTON.toString());
    }

	/**
	 * Constructs a new UnsubscribeButtonResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnsubscribeButtonResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}