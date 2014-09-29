package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add Command Response is sent, when AddCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AddCommandResponse extends RPCResponse {

    public AddCommandResponse() {
        super("AddCommand");
    }

    public AddCommandResponse(Hashtable hash) {
        super(hash);
    }
}

