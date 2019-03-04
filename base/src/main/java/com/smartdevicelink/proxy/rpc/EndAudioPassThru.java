package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * When this request is invoked, the audio capture stops
 * 
 * <p>Function Group: AudioPassThru</p>
 * 
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * 
 *  <p><b>Request</b></p>
 *  
 *  <p>No parameters.</p>
 *  <p><b>Response</b> </p>
 *  <p><b>Non-default Result Codes:</b></p>
 *  <p>SUCCESS</p>
 *  <p>INVALID_DATA</p>
 *  <p>OUT_OF_MEMORY</p>
 *  <p>TOO_MANY_PENDING_REQUESTS</p>
 *  <p>APPLICATION_NOT_REGISTERED</p>
 *  <p>GENERIC_ERROR</p>
 *  <p>REJECTED</p>
 *  <p>DISALLOWED</p>
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
	 * <p>Constructs a new EndAudioPassThru object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
     */
    public EndAudioPassThru(Hashtable<String, Object> hash) {
        super(hash);
    }
}
