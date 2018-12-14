package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * System Request Response is sent, when SystemRequest  has been called
 * 
 * @since SmartDeviceLink 3.0
 */
public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST.toString());
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SystemRequestResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SystemRequestResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}