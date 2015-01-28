package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
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
        super(jsonObject);
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
}
