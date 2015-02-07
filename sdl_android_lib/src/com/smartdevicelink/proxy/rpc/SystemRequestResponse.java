package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST);
    }
    
    /**
     * Creates a SystemRequestResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SystemRequestResponse(JSONObject jsonObject){
        super(SdlCommand.SYSTEM_REQUEST, jsonObject);
    }
}