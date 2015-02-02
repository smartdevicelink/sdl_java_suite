package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
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
        super(FunctionID.SPEAK);
    }
    
    /**
     * Creates a SpeakResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SpeakResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}