package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.util.JsonUtils;

public class DIDResult extends RPCObject {
	public static final String KEY_RESULT_CODE = "resultCode";
	public static final String KEY_DATA = "data";
	public static final String KEY_DID_LOCATION = "didLocation";
	
	private String resultCode; // represents VehicleDataResultCode enum
    private String data;
	private Integer didLocation;
	
    public DIDResult() {}

    /**
     * Creates a DIDResult object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DIDResult(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.resultCode = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RESULT_CODE);
            this.data = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DATA);
            this.didLocation = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_DID_LOCATION);
            break;
        }
    }
    
    public void setResultCode(VehicleDataResultCode resultCode) {
    	this.resultCode = (resultCode == null) ? null : resultCode.getJsonName(sdlVersion);
    }
    
    public VehicleDataResultCode getResultCode() {
        return VehicleDataResultCode.valueForJsonName(this.resultCode, sdlVersion);
    }
    
    public void setDidLocation(Integer didLocation) {
    	this.didLocation = didLocation;
    }
    
    public Integer getDidLocation() {
    	return this.didLocation;
    }
    
    public void setData(String data) {
    	this.data = data;
    }
    
    public String getData() {
    	return this.data;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_RESULT_CODE, this.resultCode);
            JsonUtils.addToJsonObject(result, KEY_DATA, this.data);
            JsonUtils.addToJsonObject(result, KEY_DID_LOCATION, this.didLocation);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((didLocation == null) ? 0 : didLocation.hashCode());
		result = prime * result + ((resultCode == null) ? 0 : resultCode.hashCode());
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
		DIDResult other = (DIDResult) obj;
		if (data == null) {
			if (other.data != null) { 
				return false;
			}
		} 
		else if (!data.equals(other.data)) { 
			return false;
		}
		if (didLocation == null) {
			if (other.didLocation != null) { 
				return false;
			}
		} 
		else if (!didLocation.equals(other.didLocation)) { 
			return false;
		}
		if (resultCode == null) {
			if (other.resultCode != null) { 
				return false;
			}
		}
		else if (!resultCode.equals(other.resultCode)) { 
			return false;
		}
		return true;
	}
}
