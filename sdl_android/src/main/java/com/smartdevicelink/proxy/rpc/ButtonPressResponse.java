package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class ButtonPressResponse extends RPCResponse {

    /**
     * Constructs a new ButtonPressResponse object
     */
    public ButtonPressResponse() {
        super(FunctionID.BUTTON_PRESS.toString());
    }

    /**
     * <p>Constructs a new ButtonPressResponse object indicated by the
     * Hashtable parameter</p>
     *
     *
     * @param hash
     * The Hashtable to use
     */
    public ButtonPressResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}
