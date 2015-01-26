package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Change Registration Response is sent, when ChangeRegistration has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ChangeRegistrationResponse extends RPCResponse {

	/**
	 * Constructs a new ChangeRegistrationResponse object
	 */
    public ChangeRegistrationResponse() {
        super(FunctionID.CHANGE_REGISTRATION);
    }

    /**
     * Creates a ChangeRegistrationResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ChangeRegistrationResponse(JSONObject json){
        super(json);
    }
}
