package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RPCResponse;

public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super(FunctionId.SYSTEM_REQUEST.toString());
    }

    public SystemRequestResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}