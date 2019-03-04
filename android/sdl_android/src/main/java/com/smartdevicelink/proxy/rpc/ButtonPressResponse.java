package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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
    /**
     * Constructs a new ButtonPressResponse object
     * @param resultCode whether the request is successfully processed
     * @param success whether the request is successfully processed
     */
    public ButtonPressResponse(@NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setResultCode(resultCode);
        setSuccess(success);
    }
}
