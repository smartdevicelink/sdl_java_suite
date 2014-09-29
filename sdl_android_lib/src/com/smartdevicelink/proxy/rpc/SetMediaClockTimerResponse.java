package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

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
        super("SetMediaClockTimer");
    }

	/**
	 * Constructs a new SetMediaClockTimerResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetMediaClockTimerResponse(Hashtable hash) {
        super(hash);
    }
}