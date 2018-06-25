package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Dial Number Response is sent, when DialNumber has been called
 * 
 * @since SmartDeviceLink 4.0
 */
public class DialNumberResponse extends RPCResponse {

    public DialNumberResponse() {
        super(FunctionID.DIAL_NUMBER.toString());
    }
    
	public DialNumberResponse(Hashtable<String, Object> hash) {
		super(hash);
	}

    /**
     * Constructs a new DialNumberResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DialNumberResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}
