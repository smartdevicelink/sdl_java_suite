package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;

public class SetAppIconResponse extends RPCResponse {

    public SetAppIconResponse() {
        super("SetAppIcon");
    }
    public SetAppIconResponse(Hashtable hash) {
        super(hash);
    }
}