package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
        super(FunctionID.UNREGISTER_APP_INTERFACE.toString());
    }

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public UnregisterAppInterfaceResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public UnregisterAppInterfaceResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}