package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Perform Audio Pass Thru Response is sent, when PerformAudioPassThru has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class PerformAudioPassThruResponse extends RPCResponse {

	/**
	 * Constructs a new PerformAudioPassThruResponse object
	 */
    public PerformAudioPassThruResponse() {
        super("PerformAudioPassThru");
    }

	/**
	 * Constructs a new PerformAudioPassThruResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PerformAudioPassThruResponse(Hashtable hash) {
        super(hash);
    }
}