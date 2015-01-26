package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add SubMenu Response is sent, when AddSubMenu has been called
 * @since SmartDeviceLink 1.0
 */
public class AddSubMenuResponse extends RPCResponse {

    public AddSubMenuResponse() {
        super(FunctionID.ADD_SUB_MENU);
    }
    
    /**
     * Creates an AddSubMenuResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AddSubMenuResponse(JSONObject json){
        super(json);
    }
}