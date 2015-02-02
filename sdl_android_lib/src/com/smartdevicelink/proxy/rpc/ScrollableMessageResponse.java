package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Scrollable Message Response is sent, when ScrollableMessage has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ScrollableMessageResponse extends RPCResponse {

	/**
	 * Constructs a new ScrollableMessageResponse object
	 */
    public ScrollableMessageResponse() {
        super(FunctionID.SCROLLABLE_MESSAGE);
    }
    
    /**
     * Creates a ScrollableMessageResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ScrollableMessageResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}