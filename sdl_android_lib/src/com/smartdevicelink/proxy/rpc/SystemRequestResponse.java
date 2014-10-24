package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionID.SYSTEM_REQUEST);
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}