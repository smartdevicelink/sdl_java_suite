package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Change Registration Response is sent, when ChangeRegistration has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ChangeRegistrationResponse extends RpcResponse {

	/**
	 * Constructs a new ChangeRegistrationResponse object
	 */
    public ChangeRegistrationResponse() {
        super(FunctionId.CHANGE_REGISTRATION.toString());
    }

	/**
	 * Constructs a new ChangeRegistrationResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ChangeRegistrationResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}
