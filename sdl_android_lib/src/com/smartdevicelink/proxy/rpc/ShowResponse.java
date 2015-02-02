package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Show Response is sent, when Show has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ShowResponse extends RPCResponse {

	/**
	 * Constructs a new ShowResponse object
	 */
    public ShowResponse() {
        super(FunctionID.SHOW);
    }
    
    /**
     * Creates a ShowResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ShowResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}