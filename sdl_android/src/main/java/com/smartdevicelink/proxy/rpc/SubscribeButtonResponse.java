package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Sub scribeButton Response is sent, when SubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new SubscribeButtonResponse object
	 */
    public SubscribeButtonResponse() {
        super(FunctionID.SUBSCRIBE_BUTTON.toString());
    }

	/**
	 * <p>Constructs a new SubscribeButtonResponse object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash The Hashtable to use
	 */
    public SubscribeButtonResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SubscribeButtonResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SubscribeButtonResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}