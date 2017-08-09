package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class ButtonPressResponse extends RPCResponse {

    public ButtonPressResponse() {
        super(FunctionID.BUTTON_PRESS.toString());
    }

    public ButtonPressResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}
