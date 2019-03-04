package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
        super(FunctionID.CHANGE_REGISTRATION.toString());
    }

	/**
	 * <p>Constructs a new ChangeRegistrationResponse object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ChangeRegistrationResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new ChangeRegistrationResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public ChangeRegistrationResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}
