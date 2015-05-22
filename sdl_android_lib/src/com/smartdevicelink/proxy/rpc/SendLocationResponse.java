package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Add SendLocation Response is sent, when SendLocation has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SendLocationResponse extends RPCResponse{

    public SendLocationResponse(){
        super(FunctionId.SEND_LOCATION.toString());
    }

    public SendLocationResponse(Hashtable<String, Object> hash){
        super(hash);
    }
}
