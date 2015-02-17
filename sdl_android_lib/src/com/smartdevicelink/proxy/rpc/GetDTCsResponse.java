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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dtc == null) ? 0 : dtc.hashCode());
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
		GetDTCsResponse other = (GetDTCsResponse) obj;
		if (dtc == null) {
			if (other.dtc != null) { 
				return false;
			}
		} 
		else if (!dtc.equals(other.dtc)) { 
			return false;
		}
		return true;
	}
}
