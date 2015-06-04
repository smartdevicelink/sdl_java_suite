package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Set Media Clock Timer Response is sent, when SetMediaClockTimer has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetMediaClockTimerResponse extends RpcResponse {

	/**
	 * Constructs a new SetMediaClockTimerResponse object
	 */
    public SetMediaClockTimerResponse() {
        super(FunctionId.SET_MEDIA_CLOCK_TIMER.toString());
    }

	/**
	 * Constructs a new SetMediaClockTimerResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetMediaClockTimerResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}