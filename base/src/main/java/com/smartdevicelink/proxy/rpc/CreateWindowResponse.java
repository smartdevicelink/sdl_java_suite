package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * This RCP creates a window, by default the main is 0.
 * @since 6.0
 */
public class CreateWindowResponse extends RPCResponse {

    /**
     * Constructs a new CreateWindowResponse object
     */
    public CreateWindowResponse() {
        super(FunctionID.CREATE_WINDOW.toString());
    }

    /**
     * <p>Constructs a new CreateWindowResponse object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public CreateWindowResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new CreateWindowResponse object
     *
     * @param success    whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public CreateWindowResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}