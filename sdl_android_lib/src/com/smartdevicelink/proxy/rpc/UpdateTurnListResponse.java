package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Update Turn List Response is sent, when UpdateTurnList has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class UpdateTurnListResponse extends RpcResponse{

    /**
     * Constructs a new UpdateTurnListResponse object
     */
    public UpdateTurnListResponse() {
        super(FunctionId.UPDATE_TURN_LIST.toString());
    }
    
    public UpdateTurnListResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

}
