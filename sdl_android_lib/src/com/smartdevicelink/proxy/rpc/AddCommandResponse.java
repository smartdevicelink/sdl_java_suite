package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add Command Response is sent, when AddCommand has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AddCommandResponse extends RPCResponse {
	/**
	 * Constructs a new AddCommandResponse object
	 */
    public AddCommandResponse() {
        super(FunctionID.ADD_COMMAND.toString());
    }

    public AddCommandResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}

