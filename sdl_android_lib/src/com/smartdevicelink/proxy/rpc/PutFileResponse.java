package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Put File Response is sent, when PutFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class PutFileResponse extends RPCResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

	private Integer spaceAvailable;
	
	/**
	 * Constructs a new PutFileResponse object
	 */
    public PutFileResponse() {
        super(FunctionID.PUT_FILE);
    }

    /**
     * Creates a PutFileResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PutFileResponse(JSONObject jsonObject){
        super(SdlCommand.PUT_FILE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.spaceAvailable = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_SPACE_AVAILABLE);
            break;
        }
    }

    public void setSpaceAvailable(Integer spaceAvailable) {
        this.spaceAvailable = spaceAvailable;
    }
    
    public Integer getSpaceAvailable() {
        return this.spaceAvailable;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_SPACE_AVAILABLE, this.spaceAvailable);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((spaceAvailable == null) ? 0 : spaceAvailable.hashCode());
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
		PutFileResponse other = (PutFileResponse) obj;
		if (spaceAvailable == null) {
			if (other.spaceAvailable != null) { 
				return false;
			}
		} else if (!spaceAvailable.equals(other.spaceAvailable)) { 
			return false;
		}
		return true;
	}
}
