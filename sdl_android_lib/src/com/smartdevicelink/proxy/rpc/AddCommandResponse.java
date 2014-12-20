package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add Command Response is sent, when AddCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AddCommandResponse extends RPCResponse {

    public AddCommandResponse() {
        super(FunctionID.ADD_COMMAND);
    }
    
    public AddCommandResponse(JSONObject json, int sdlVersion){
        super(json, sdlVersion);
    }
}

