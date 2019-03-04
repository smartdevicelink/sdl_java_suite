package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Unsubscribe Button Response is sent, when UnsubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnsubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new UnsubscribeButtonResponse object
	 */
    public UnsubscribeButtonResponse() {
        super(FunctionID.UNSUBSCRIBE_BUTTON.toString());
    }

	/**
	 * Constructs a new UnsubscribeButtonResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash The Hashtable to use
	 */
    public UnsubscribeButtonResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new UnsubscribeButtonResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public UnsubscribeButtonResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}