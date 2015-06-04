package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Show Constant TBT Response is sent, when ShowConstantTBT has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ShowConstantTbtResponse extends RpcResponse{

    public ShowConstantTbtResponse() {
        super(FunctionId.SHOW_CONSTANT_TBT.toString());
    }
    
    public ShowConstantTbtResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

}
