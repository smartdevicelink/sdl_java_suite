package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete Command Response is sent, when DeleteCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteCommandResponse extends RPCResponse {

    public DeleteCommandResponse() {
        super(FunctionID.DELETE_COMMAND);
    }
    
    /**
     * Creates a DeleteCommandResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteCommandResponse(JSONObject jsonObject) {
        super(jsonObject);
    }
}