package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new AddCommandResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public AddCommandResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}

