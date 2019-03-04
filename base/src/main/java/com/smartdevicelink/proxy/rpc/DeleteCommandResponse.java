package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new DeleteCommandResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DeleteCommandResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}