package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
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
        super(FunctionID.END_AUDIO_PASS_THRU.toString());
    }
    
    /**
	 * Constructs a new EndAudioPassThru object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
     */
    public EndAudioPassThru(Hashtable<String, Object> hash) {
        super(hash);
    }
}
