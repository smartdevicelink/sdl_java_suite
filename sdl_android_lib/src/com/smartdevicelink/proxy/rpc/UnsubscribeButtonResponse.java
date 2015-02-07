package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Unsubscribe Button Response is sent, when UnsubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnsubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new UnsubscribeButtonResponse object
	 */
    public UnsubscribeButtonResponse() {
        super(FunctionID.UNSUBSCRIBE_BUTTON);
    }
    
    /**
     * Creates a UnsubscribeButtonResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public UnsubscribeButtonResponse(JSONObject jsonObject){
        super(SdlCommand.UNSUBSCRIBE_BUTTON, jsonObject);
    }
}