package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Delete SubMenu Response is sent, when DeleteSubMenu has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteSubMenuResponse extends RPCResponse {

    public DeleteSubMenuResponse() {
        super(FunctionID.DELETE_SUB_MENU);
    }
    
    /**
     * Creates a DeleteSubMenuResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteSubMenuResponse(JSONObject jsonObject) {
        super(SdlCommand.DELETE_SUB_MENU, jsonObject);
    }
}