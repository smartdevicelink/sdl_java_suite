package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.util.JsonUtils;

/**
 * Alert Response is sent, when Alert has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AlertResponse extends RPCResponse {
	public static final String KEY_TRY_AGAIN_TIME = "tryAgainTime";

	private Integer tryAgainTime;
	
	/**
	 * Constructs a new AlertResponse object
	 */
    public AlertResponse() {
        super(FunctionID.ALERT);
    }

    /**
     * Creates an AlertResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AlertResponse(JSONObject jsonObject) {
        super(jsonObject);
        
        switch(sdlVersion){
        default:
            this.tryAgainTime = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TRY_AGAIN_TIME);
            break;
        }
    }
    
    public Integer getTryAgainTime() {
        return tryAgainTime;
    }
    
    public void setTryAgainTime(Integer tryAgainTime) {
        this.tryAgainTime = tryAgainTime;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TRY_AGAIN_TIME, this.tryAgainTime);
            break;
        }
        
        return result;
    }
    
    
}
