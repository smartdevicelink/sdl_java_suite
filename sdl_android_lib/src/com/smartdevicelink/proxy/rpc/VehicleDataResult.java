package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.util.JsonUtils;

public class VehicleDataResult extends RPCObject {
	public static final String KEY_DATA_TYPE = "dataType";
	public static final String KEY_RESULT_CODE = "resultCode";

	private String dataType; // represents VehicleDataType enum
	private String resultCode; // represents VehicleDataResultCode enum
	
    public VehicleDataResult() { }
    
    /**
     * Creates a VehicleDataResult object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public VehicleDataResult(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.dataType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DATA_TYPE);
            this.resultCode = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RESULT_CODE);
            break;
        }
    }
    
    public void setDataType(VehicleDataType dataType) {
    	this.dataType = (dataType == null) ? null : dataType.getJsonName(sdlVersion);
    }
    
    public VehicleDataType getDataType() {
        return VehicleDataType.valueForJsonName(this.dataType, sdlVersion);
    }
    
    public void setResultCode(VehicleDataResultCode resultCode) {
    	this.resultCode = (resultCode == null) ? null : resultCode.getJsonName(sdlVersion);
    }
    
    public VehicleDataResultCode getResultCode() {
        return VehicleDataResultCode.valueForJsonName(this.resultCode, sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DATA_TYPE, this.dataType);
            JsonUtils.addToJsonObject(result, KEY_RESULT_CODE, this.resultCode);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((resultCode == null) ? 0 : resultCode.hashCode());
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
		VehicleDataResult other = (VehicleDataResult) obj;
		if (dataType == null) {
			if (other.dataType != null) { 
				return false;
			}
		} else if (!dataType.equals(other.dataType)) { 
			return false;
		}
		if (resultCode == null) {
			if (other.resultCode != null) { 
				return false;
			}
		} else if (!resultCode.equals(other.resultCode)) { 
			return false;
		}
		return true;
	}
}
