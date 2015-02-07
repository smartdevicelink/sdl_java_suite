package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

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
        super(FunctionID.END_AUDIO_PASS_THRU);
    }
    
    /**
     * Creates an EndAudioPassThru object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public EndAudioPassThru(JSONObject jsonObject) {
        super(SdlCommand.END_AUDIO_PASSTHRU, jsonObject);
    }
}
