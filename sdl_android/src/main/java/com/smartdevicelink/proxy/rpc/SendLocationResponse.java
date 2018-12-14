package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Add SendLocation Response is sent, when SendLocation has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SendLocationResponse extends RPCResponse{

    public SendLocationResponse(){
        super(FunctionID.SEND_LOCATION.toString());
    }

    public SendLocationResponse(Hashtable<String, Object> hash){
        super(hash);
    }

    /**
     * Constructs a new SendLocationResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public SendLocationResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}
