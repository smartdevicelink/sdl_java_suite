package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

public class DiagnosticMessage extends RPCRequest {
	public static final String KEY_TARGET_ID = "targetID";
	public static final String KEY_MESSAGE_LENGTH = "messageLength";
	public static final String KEY_MESSAGE_DATA = "messageData";

    public DiagnosticMessage() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }

    public DiagnosticMessage(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setTargetID(Integer targetID) {
    	if (targetID != null) {
    		parameters.put(KEY_TARGET_ID, targetID);
    	} else {
    		parameters.remove(KEY_TARGET_ID);
    	}
    }
    public Integer getTargetID() {
    	return (Integer) parameters.get(KEY_TARGET_ID);
    }    

    public void setMessageLength(Integer messageLength) {
    	if (messageLength != null) {
    		parameters.put(KEY_MESSAGE_LENGTH, messageLength);
    	} else {
    		parameters.remove(KEY_MESSAGE_LENGTH);
    	}
    }
    public Integer getMessageLength() {
    	return (Integer) parameters.get(KEY_MESSAGE_LENGTH);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMessageData() {
    	if(parameters.get(KEY_MESSAGE_DATA) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(KEY_MESSAGE_DATA);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (List<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    
    public void setMessageData(List<Integer> messageData) {
        if (messageData != null) {
            parameters.put(KEY_MESSAGE_DATA, messageData);
        } else {
        	parameters.remove(KEY_MESSAGE_DATA);
        }
    }    
}
