package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Generic Response is sent, when the name of a received msg cannot be
 * retrieved. Only used in case of an error. Currently, only resultCode
 * INVALID_DATA is used.
*/
public class GenericResponse extends RPCResponse {

    public GenericResponse() {
        super(FunctionID.GENERIC_RESPONSE);
    }
    
    /**
     * Creates a GenericResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public GenericResponse(JSONObject jsonObject) {
        super(SdlCommand.GENERIC_RESPONSE, jsonObject);
    }
}