package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Add SendLocation Response is sent, when SendLocation has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SendLocationResponse extends RPCResponse{

    public SendLocationResponse() {
        super(FunctionID.SEND_LOCATION);
    }
    
    /**
     * Creates an SendLocationResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SendLocationResponse(JSONObject json){
        super(SdlCommand.SEND_LOCATION, json);
    }
}
