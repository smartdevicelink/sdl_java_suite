package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDIDResponse extends RPCResponse {
	public static final String KEY_DID_RESULT = "didResult";

	private List<DIDResult> didResult;
	
    public ReadDIDResponse() {
        super(FunctionID.READ_DID);
    }

    /**
     * Creates a ReadDIDResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ReadDIDResponse(JSONObject jsonObject){
        super(SdlCommand.READ_DIDS, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            List<JSONObject> didResultObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_DID_RESULT);
            if(didResultObjs != null){
                this.didResult = new ArrayList<DIDResult>(didResultObjs.size());
                for(JSONObject didResultObj : didResultObjs){
                    this.didResult.add(new DIDResult(didResultObj));
                }
            }
            break;
        }
    }

    public void setDidResult(List<DIDResult> didResult) {
    	this.didResult = didResult;
    }
    
    public List<DIDResult> getDidResult() {
        return this.didResult;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DID_RESULT, (this.didResult == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.didResult, sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((didResult == null) ? 0 : didResult.hashCode());
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
		ReadDIDResponse other = (ReadDIDResponse) obj;
		if (didResult == null) {
			if (other.didResult != null) { 
				return false;
			}
		} else if (!didResult.equals(other.didResult)) { 
			return false;
		}
		return true;
	}
}
