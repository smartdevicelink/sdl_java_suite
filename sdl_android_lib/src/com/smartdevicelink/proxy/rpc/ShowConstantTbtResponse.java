package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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

}
