package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Unregister AppInterface Response is sent, when UnregisterAppInterface has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnregisterAppInterfaceResponse extends RpcResponse {

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object
	 */
    public UnregisterAppInterfaceResponse() {
        super(FunctionId.UNREGISTER_APP_INTERFACE.toString());
    }

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnregisterAppInterfaceResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}