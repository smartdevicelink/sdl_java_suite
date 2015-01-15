package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;
/**
 * System Request Response is sent, when SystemRequest  has been called
 * 
 * @since SmartDeviceLink 3.0
 */
public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST);
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}