package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

public class DiagnosticMessage extends RPCRequest {
	public static final String KEY_TARGET_ID = "targetID";
	public static final String KEY_MESSAGE_LENGTH = "messageLength";
	public static final String KEY_MESSAGE_DATA = "messageData";

	private Integer targetId, messageLength;
	private List<Integer> messageData;
	
    public DiagnosticMessage() {
        super(FunctionID.DIAGNOSTIC_MESSAGE);
    }

    /**
     * Creates a DiagnosticMessage object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DiagnosticMessage(JSONObject jsonObject) {
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.targetId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TARGET_ID);
            this.messageLength = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MESSAGE_LENGTH);
            this.messageData = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_MESSAGE_DATA);
            break;
        }
    }
    
    public void setTargetID(Integer targetID) {
    	this.targetId = targetID;
    }
    
    public Integer getTargetID() {
    	return this.targetId;
    }

    public void setMessageLength(Integer messageLength) {
    	this.messageLength = messageLength;
    }
    
    public Integer getMessageLength() {
    	return this.messageLength;
    }

    public List<Integer> getMessageData() {
    	return this.messageData;
    }
    
    public void setMessageData(List<Integer> messageData) {
        this.messageData = messageData;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TARGET_ID, this.targetId);
            JsonUtils.addToJsonObject(result, KEY_MESSAGE_LENGTH, this.messageLength);
            JsonUtils.addToJsonObject(result, KEY_MESSAGE_DATA, 
                    JsonUtils.createJsonArray(this.messageData));
            break;
        }
        
        return result;
    }
}
