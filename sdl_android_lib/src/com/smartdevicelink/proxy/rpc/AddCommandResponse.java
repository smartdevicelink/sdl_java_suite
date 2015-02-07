package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Add Command Response is sent, when AddCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AddCommandResponse extends RPCResponse {

    public AddCommandResponse() {
        super(FunctionID.ADD_COMMAND);
    }
    
    /**
     * Creates an AddCommandResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AddCommandResponse(JSONObject json){
        super(SdlCommand.ADD_COMMAND, json);
    }
}

