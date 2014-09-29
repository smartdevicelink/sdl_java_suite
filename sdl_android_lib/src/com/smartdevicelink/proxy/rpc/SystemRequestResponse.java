package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class SystemRequestResponse extends RPCResponse {
    public SystemRequestResponse() {
        super("SystemRequest");
    }

    public SystemRequestResponse(Hashtable hash) {
        super(hash);
    }
}