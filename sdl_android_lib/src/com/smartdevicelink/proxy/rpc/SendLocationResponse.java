package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
}
