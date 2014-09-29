package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

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
        super("SetGlobalProperties");
    }

	/**
	 * Constructs a new SetGlobalPropertiesResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetGlobalPropertiesResponse(Hashtable hash) {
        super(hash);
    }
}