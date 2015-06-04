package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Generic Response is sent, when the name of a received msg cannot be
 * retrieved. Only used in case of an error. Currently, only resultCode
 * INVALID_DATA is used.
*/
public class GenericResponse extends RpcResponse {

    public GenericResponse() {
        super(FunctionId.GENERIC_RESPONSE.toString());
    }
    public GenericResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}