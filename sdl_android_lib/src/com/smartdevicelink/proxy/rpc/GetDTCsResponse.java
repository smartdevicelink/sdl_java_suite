package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Get DTCs Response is sent, when GetDTCs has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetDTCsResponse extends RPCResponse {
	public static final String KEY_DTC = "dtc";

	private List<String> dtc;
	
    public GetDTCsResponse() {
        super(FunctionID.GET_DTCS);
    }
    
    public GetDTCsResponse(JSONObject jsonObject) {
        super(SdlCommand.GET_DTCS, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.dtc = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_DTC);
            break;
        }
    }
    
    public List<String> getDtc() {
    	return this.dtc;
    }
    
    public void setDtc(List<String> dtc) {
        this.dtc = dtc;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DTC, 
                    (this.dtc == null) ? null : JsonUtils.createJsonArray(this.dtc));
            break;
        }
        
        return result;
    }
}
