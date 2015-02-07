package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Set Global Properties Response is sent, when SetGlobalProperties has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetGlobalPropertiesResponse extends RPCResponse {

	/**
	 * Constructs a new SetGlobalPropertiesResponse object
	 */
    public SetGlobalPropertiesResponse() {
        super(FunctionID.SET_GLOBAL_PROPERTIES);
    }
    
    /**
     * Creates a SetGlobalPropertiesResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetGlobalPropertiesResponse(JSONObject jsonObject){
        super(SdlCommand.SET_GLOBAL_PROPERTIES, jsonObject);
    }
}