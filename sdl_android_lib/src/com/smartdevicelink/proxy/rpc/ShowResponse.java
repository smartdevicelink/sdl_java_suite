package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
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
        super(FunctionID.SHOW.toString());
    }

	/**
	 * Constructs a new ShowResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ShowResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}