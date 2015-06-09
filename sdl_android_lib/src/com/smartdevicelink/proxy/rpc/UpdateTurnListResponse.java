package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
        super(FunctionID.UPDATE_TURN_LIST.toString());
    }
    
    public UpdateTurnListResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

}
