package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * End Audio Pass Thru Response is sent, when EndAudioPassThru has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class EndAudioPassThruResponse extends RPCResponse {

	/**
	 * Constructs a new EndAudioPassThruResponse object
	 */
    public EndAudioPassThruResponse() {
        super(FunctionID.END_AUDIO_PASS_THRU);
    }
    
    /**
     * Creates an EndAudioPassThruResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public EndAudioPassThruResponse(JSONObject jsonObject) {
        super(SdlCommand.END_AUDIO_PASSTHRU, jsonObject);
    }
}