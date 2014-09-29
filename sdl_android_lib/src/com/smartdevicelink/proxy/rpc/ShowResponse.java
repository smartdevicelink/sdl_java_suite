package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Show Response is sent, when Show has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ShowResponse extends RPCResponse {

	/**
	 * Constructs a new ShowResponse object
	 */
    public ShowResponse() {
        super("Show");
    }

	/**
	 * Constructs a new ShowResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ShowResponse(Hashtable hash) {
        super(hash);
    }
}