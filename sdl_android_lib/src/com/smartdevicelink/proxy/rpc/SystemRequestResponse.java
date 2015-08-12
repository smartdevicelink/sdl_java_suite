package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * System Request Response is sent, when SystemRequest  has been called
 * 
 * @since SmartDeviceLink 3.0
 */
public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST.toString());
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}