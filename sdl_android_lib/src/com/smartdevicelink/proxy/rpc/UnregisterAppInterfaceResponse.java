package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Unregister AppInterface Response is sent, when UnregisterAppInterface has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class UnregisterAppInterfaceResponse extends RPCResponse {

	/**
	 * Constructs a new UnregisterAppInterfaceResponse object
	 */
    public UnregisterAppInterfaceResponse() {
        super(FunctionID.UNREGISTER_APP_INTERFACE);
    }
    
    /**
     * Creates a UnregisterAppInterfaceResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public UnregisterAppInterfaceResponse(JSONObject jsonObject){
        super(SdlCommand.UNREGISTER_APP_INTERFACE, jsonObject);
    }
}