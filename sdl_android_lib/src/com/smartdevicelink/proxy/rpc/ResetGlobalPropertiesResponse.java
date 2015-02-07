package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Reset Global Properties Response is sent, when ResetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class ResetGlobalPropertiesResponse extends RPCResponse {

	/**
	 * Constructs a new ResetGlobalPropertiesResponse object
	 */
    public ResetGlobalPropertiesResponse() {
        super(FunctionID.RESET_GLOBAL_PROPERTIES);
    }
    
    /**
     * Creates a ResetGlobalPropertiesResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ResetGlobalPropertiesResponse(JSONObject jsonObject){
        super(SdlCommand.RESET_GLOBAL_PROPERTIES, jsonObject);
    }
}