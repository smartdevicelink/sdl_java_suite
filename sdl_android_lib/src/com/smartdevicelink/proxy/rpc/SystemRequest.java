package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.BulkData;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.JsonUtils;

public class SystemRequest extends RPCRequest implements BulkData{
	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_REQUEST_TYPE = "requestType";
	public static final String KEY_DATA = "data";
	
	private List<String> legacyData;
	private String filename;
	private String requestType; // represents RequestType enum
	
	private byte[] bulkData;
	
    public SystemRequest() {
        super(FunctionID.SYSTEM_REQUEST);
    }

	public SystemRequest(boolean bLegacy) {
        super(FunctionID.ENCODED_SYNC_P_DATA);
    }
    
    /**
     * Creates a SystemRequest object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SystemRequest(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.filename = JsonUtils.readStringFromJsonObject(jsonObject, KEY_FILE_NAME);
            this.requestType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_REQUEST_TYPE);
            this.legacyData = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_DATA);
            break;
        }
    }

    public List<String> getLegacyData() {
        return this.legacyData;
    }
 
    public void setLegacyData( List<String> data ) {
    	this.legacyData = data;
    }    
            
    public String getFileName() {
        return this.filename;
    }
    
    public void setFileName(String fileName) {
        this.filename = fileName;
    }    

    public RequestType getRequestType() {
        return RequestType.valueForJsonName(this.requestType, sdlVersion);
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = (requestType == null) ? null : requestType.getJsonName(sdlVersion);
    }

    @Override
    public byte[] getBulkData(){
        return this.bulkData;
    }

    @Override
    public void setBulkData(byte[] rawData){
        this.bulkData = rawData;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_FILE_NAME, this.filename);
            JsonUtils.addToJsonObject(result, KEY_REQUEST_TYPE, this.requestType);
            JsonUtils.addToJsonObject(result, KEY_DATA, (this.legacyData == null) ? null :
                JsonUtils.createJsonArray(this.legacyData));
            break;
        }
        
        return result;
    }
}
