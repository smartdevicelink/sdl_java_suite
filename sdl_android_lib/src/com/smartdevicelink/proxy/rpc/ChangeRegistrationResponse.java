package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Change Registration Response is sent, when ChangeRegistration has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ChangeRegistrationResponse extends RPCResponse {

	/**
	 * Constructs a new ChangeRegistrationResponse object
	 */
    public ChangeRegistrationResponse() {
        super("ChangeLanguageRegistration");
    }

	/**
	 * Constructs a new ChangeRegistrationResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ChangeRegistrationResponse(Hashtable hash) {
        super(hash);
    }
}