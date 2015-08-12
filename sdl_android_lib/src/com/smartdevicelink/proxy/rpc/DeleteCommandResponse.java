package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Delete Command Response is sent, when DeleteCommand has been called.
 * 
 * @since SmartDeviceLink 1.0
 */
public class DeleteCommandResponse extends RPCResponse {
	/** Constructs a new DeleteCommandResponse object
	 * 
	 */

    public DeleteCommandResponse() {
        super(FunctionID.DELETE_COMMAND.toString());
    }
    public DeleteCommandResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}