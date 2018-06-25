package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Speak Response is sent, when Speak has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SpeakResponse extends RPCResponse {

	/**
	 * Constructs a new SpeakResponse object
	 */
    public SpeakResponse() {
        super(FunctionID.SPEAK.toString());
    }

	/**
	 * Constructs a new SpeakResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SpeakResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SpeakResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SpeakResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

}