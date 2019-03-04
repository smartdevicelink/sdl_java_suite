package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Show Constant TBT Response is sent, when ShowConstantTBT has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ShowConstantTbtResponse extends RPCResponse{

    public ShowConstantTbtResponse() {
        super(FunctionID.SHOW_CONSTANT_TBT.toString());
    }
    
    public ShowConstantTbtResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ShowConstantTbtResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public ShowConstantTbtResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}
