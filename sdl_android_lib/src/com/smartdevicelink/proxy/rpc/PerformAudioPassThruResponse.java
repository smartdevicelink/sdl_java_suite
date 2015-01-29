package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
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
        super(FunctionID.PERFORM_AUDIO_PASS_THRU);
    }


    /**
     * Creates a PerformAudioPassThruResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PerformAudioPassThruResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}