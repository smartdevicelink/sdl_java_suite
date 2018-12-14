package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Set App Icon Response is sent, when SetAppIcon has been called.
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetAppIconResponse extends RPCResponse {
	/**
	 * Constructs a new SetAppIconResponse object
	 */

    public SetAppIconResponse() {
        super(FunctionID.SET_APP_ICON.toString());
    }
    /**
	 * Constructs a new SetAppIconResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */

    public SetAppIconResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SetAppIconResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}