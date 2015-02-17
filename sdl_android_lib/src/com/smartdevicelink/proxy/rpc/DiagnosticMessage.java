package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.DIAGNOSTIC_MESSAGE, jsonObject);
        jsonObject = getParameters(jsonObject);
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
                    (this.messageData == null) ? null : JsonUtils.createJsonArray(this.messageData));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageData == null) ? 0 : messageData.hashCode());
		result = prime * result + ((messageLength == null) ? 0 : messageLength.hashCode());
		result = prime * result + ((targetId == null) ? 0 : targetId.hashCode());
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
		DiagnosticMessage other = (DiagnosticMessage) obj;
		if (messageData == null) {
			if (other.messageData != null) { 
				return false;
			}
		}
		else if (!messageData.equals(other.messageData)) { 
			return false;
		}
		if (messageLength == null) {
			if (other.messageLength != null) { 
				return false;
			}
		}
		else if (!messageLength.equals(other.messageLength)) { 
			return false;
		}
		if (targetId == null) {
			if (other.targetId != null) { 
				return false;
			}
		} 
		else if (!targetId.equals(other.targetId)) { 
			return false;
		}
		return true;
	}
}
