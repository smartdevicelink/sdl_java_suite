package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Generic Response is sent, when the name of a received msg cannot be
 * retrieved. Only used in case of an error. Currently, only resultCode
 * INVALID_DATA is used.
*/
public class GenericResponse extends RPCResponse {

    public GenericResponse() {
        super(FunctionID.GENERIC_RESPONSE.toString());
    }
    public GenericResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}