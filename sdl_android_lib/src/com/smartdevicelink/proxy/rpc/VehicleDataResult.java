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
}
