package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Unregister AppInterface Response is sent, when UnregisterAppInterface has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnregisterAppInterfaceResponse extends RPCResponse {

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object
	 */
    public UnregisterAppInterfaceResponse() {
        super("UnregisterAppInterface");
    }

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnregisterAppInterfaceResponse(Hashtable hash) {
        super(hash);
    }
}