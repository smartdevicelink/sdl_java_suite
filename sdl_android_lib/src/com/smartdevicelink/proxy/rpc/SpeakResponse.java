package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

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
        super("Speak");
    }

	/**
	 * Constructs a new SpeakResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SpeakResponse(Hashtable hash) {
        super(hash);
    }
}