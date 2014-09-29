package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;

/**
 * When this request is invoked, the audio capture stops
 * <p>
 * Function Group: AudioPassThru
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * @since SmartDeviceLink 2.0
 * @see PerformAudioPassThru
 */
public class EndAudioPassThru extends RPCRequest {

	/**
	 * Constructs a new EndAudioPassThru object
	 */
    public EndAudioPassThru() {
        super("EndAudioPassThru");
    }
    
    /**
	 * Constructs a new EndAudioPassThru object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
     */
    public EndAudioPassThru(Hashtable hash) {
        super(hash);
    }
}
