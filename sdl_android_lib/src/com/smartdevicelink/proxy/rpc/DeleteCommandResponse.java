package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete Command Response is sent, when DeleteCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteCommandResponse extends RPCResponse {

    public DeleteCommandResponse() {
        super("DeleteCommand");
    }
    public DeleteCommandResponse(Hashtable hash) {
        super(hash);
    }
}