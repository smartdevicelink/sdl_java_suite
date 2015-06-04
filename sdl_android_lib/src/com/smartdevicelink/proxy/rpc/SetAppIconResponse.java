package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

public class SetAppIconResponse extends RpcResponse {

    public SetAppIconResponse() {
        super(FunctionId.SET_APP_ICON.toString());
    }
    public SetAppIconResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}