package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST.toString());
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}