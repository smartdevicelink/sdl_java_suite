package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Update Turn List Response is sent, when UpdateTurnList has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class UpdateTurnListResponse extends RPCResponse{

    /**
     * Constructs a new UpdateTurnListResponse object
     */
    public UpdateTurnListResponse() {
        super(FunctionID.UPDATE_TURN_LIST);
    }
    
    public UpdateTurnListResponse(JSONObject jsonObject) {
        super(SdlCommand.UPDATE_TURN_LIST, jsonObject);
    }

}
