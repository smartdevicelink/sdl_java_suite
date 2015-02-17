package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

public class DiagnosticMessageResponse extends RPCResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";

	private List<Integer> messageDataResult;
	
    public DiagnosticMessageResponse() {
        super(FunctionID.DIAGNOSTIC_MESSAGE);
    }

    /**
     * Creates a DiagnosticMessageResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DiagnosticMessageResponse(JSONObject jsonObject) {
        super(SdlCommand.DIAGNOSTIC_MESSAGE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.messageDataResult = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_MESSAGE_DATA_RESULT);
            break;
        }
    }
    
    public List<Integer> getMessageDataResult() {
    	return this.messageDataResult;
    }
    
    public void setMessageDataResult(List<Integer> messageDataResult) {
        this.messageDataResult = messageDataResult;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MESSAGE_DATA_RESULT, 
                    (this.messageDataResult == null) ? null : JsonUtils.createJsonArray(this.messageDataResult));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageDataResult == null) ? 0 : messageDataResult.hashCode());
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
		DiagnosticMessageResponse other = (DiagnosticMessageResponse) obj;
		if (messageDataResult == null) {
			if (other.messageDataResult != null) { 
				return false;
			}
		} else if (!messageDataResult.equals(other.messageDataResult)) { 
			return false;
		}
		return true;
	}
}
