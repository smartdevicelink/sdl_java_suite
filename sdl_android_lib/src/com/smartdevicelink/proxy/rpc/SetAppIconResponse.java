package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

public class SetAppIconResponse extends RPCResponse {

    public SetAppIconResponse() {
        super(FunctionID.SET_APP_ICON);
    }
    
    /**
     * Creates a SetAppIconResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetAppIconResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}