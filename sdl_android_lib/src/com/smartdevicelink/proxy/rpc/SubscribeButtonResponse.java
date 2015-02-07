package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

/**
 * Sub scribeButton Response is sent, when SubscribeButton has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SubscribeButtonResponse extends RPCResponse {

	/**
	 * Constructs a new SubscribeButtonResponse object
	 */
    public SubscribeButtonResponse() {
        super(FunctionID.SUBSCRIBE_BUTTON);
    }
    /**
     * Creates a SubscribeButtonResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SubscribeButtonResponse(JSONObject jsonObject){
        super(SdlCommand.SUBSCRIBE_BUTTON, jsonObject);
    }
}