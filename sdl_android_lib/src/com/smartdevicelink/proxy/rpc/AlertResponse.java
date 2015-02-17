package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.ALERT, jsonObject);
        jsonObject = getParameters(jsonObject);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tryAgainTime == null) ? 0 : tryAgainTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		AlertResponse other = (AlertResponse) obj;
		if (tryAgainTime == null) {
			if (other.tryAgainTime != null) { 
				return false;
			}
		} 
		else if (!tryAgainTime.equals(other.tryAgainTime)) { 
			return false;
		}
		return true;
	}
    
    
}
