package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Set Media Clock Timer Response is sent, when SetMediaClockTimer has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class SetMediaClockTimerResponse extends RPCResponse {

	/**
	 * Constructs a new SetMediaClockTimerResponse object
	 */
    public SetMediaClockTimerResponse() {
        super(FunctionID.SET_MEDIA_CLOCK_TIMER);
    }
    
    /**
     * Creates a SetMediaClockTimerResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetMediaClockTimerResponse(JSONObject jsonObject){
        super(jsonObject);
    }
}