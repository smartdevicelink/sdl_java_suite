package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Show Constant TBT Response is sent, when ShowConstantTBT has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ShowConstantTbtResponse extends RPCResponse{

    public ShowConstantTbtResponse() {
        super(FunctionID.SHOW_CONSTANT_TBT);
    }
    
    public ShowConstantTbtResponse(JSONObject jsonObject) {
        super(SdlCommand.SHOW_CONSTANT_TBT, jsonObject);
    }

}
