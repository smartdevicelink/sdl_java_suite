package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

public class OnHashChange extends RPCNotification {
	public static final String KEY_HASH_ID = "hashID";

	private String hash;
	
    public OnHashChange() {
        super(FunctionID.ON_HASH_CHANGE);
    }

    /**
     * Creates an OnHashChange object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnHashChange(JSONObject jsonObject) {
        super(SdlCommand.ON_HASH_CHANGE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.hash = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HASH_ID);
            break;
        }
    }
    
    public String getHashID() {
        return this.hash;
    }
   
    public void setHashID(String hashID) {
        this.hash = hashID;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_HASH_ID, this.hash);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hash == null) ? 0 : hash.hashCode());
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
		OnHashChange other = (OnHashChange) obj;
		if (hash == null) {
			if (other.hash != null) { 
				return false;
			}
		} 
		else if (!hash.equals(other.hash)) { 
			return false;
		}
		return true;
	}
    
}
