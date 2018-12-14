package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Show Response is sent, when Show has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ShowResponse extends RPCResponse {

	/**
	 * Constructs a new ShowResponse object
	 */
    public ShowResponse() {
        super(FunctionID.SHOW.toString());
    }

	/**
	 * Constructs a new ShowResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ShowResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new ShowResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public ShowResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}