package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Set Media Clock Timer Response is sent, when SetMediaClockTimer has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetMediaClockTimerResponse extends RPCResponse {

	/**
	 * Constructs a new SetMediaClockTimerResponse object
	 */
    public SetMediaClockTimerResponse() {
        super(FunctionID.SET_MEDIA_CLOCK_TIMER.toString());
    }

	/**
	 * Constructs a new SetMediaClockTimerResponse object indicated by the Hashtable
	 * parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetMediaClockTimerResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Constructs a new SetMediaClockTimerResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SetMediaClockTimerResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}