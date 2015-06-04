package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Delete Command Response is sent, when DeleteCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteCommandResponse extends RpcResponse {

    public DeleteCommandResponse() {
        super(FunctionId.DELETE_COMMAND.toString());
    }
    public DeleteCommandResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}