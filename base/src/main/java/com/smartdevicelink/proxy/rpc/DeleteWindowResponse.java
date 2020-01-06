package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * This RPC is the response of the DeleteWindow RPC
 * @see DeleteWindow
 * @since 6.0
 */
public class DeleteWindowResponse extends RPCResponse {

    /**
     * Constructs a new DeleteWindowResponse object
     */
    public DeleteWindowResponse() {
        super(FunctionID.DELETE_WINDOW.toString());
    }

    /**
     * <p>Constructs a new DeleteWindowResponse object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public DeleteWindowResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new DeleteWindowResponse object
     *
     * @param success    whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DeleteWindowResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}