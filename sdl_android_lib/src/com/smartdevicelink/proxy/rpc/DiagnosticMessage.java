package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCRequest;

public class DiagnosticMessage extends RPCRequest {
	public static final String targetID = "targetID";
	public static final String messageLength = "messageLength";
	public static final String messageData = "messageData";

    public DiagnosticMessage() {
        super("DiagnosticMessage");
    }

    public DiagnosticMessage(Hashtable hash) {
        super(hash);
    }
    
    public void setTargetID(Integer targetID) {
    	if (targetID != null) {
    		parameters.put(DiagnosticMessage.targetID, targetID);
    	} else {
    		parameters.remove(DiagnosticMessage.targetID);
    	}
    }
    public Integer getTargetID() {
    	return (Integer) parameters.get(DiagnosticMessage.targetID);
    }    

    public void setMessageLength(Integer messageLength) {
    	if (messageLength != null) {
    		parameters.put(DiagnosticMessage.messageLength, messageLength);
    	} else {
    		parameters.remove(DiagnosticMessage.messageLength);
    	}
    }
    public Integer getMessageLength() {
    	return (Integer) parameters.get(DiagnosticMessage.messageLength);
    }

    public List<Integer> getMessageData() {
    	if(parameters.get(DiagnosticMessage.messageData) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(DiagnosticMessage.messageData);
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
            parameters.put(DiagnosticMessage.messageData, messageData);
        } else {
        	parameters.remove(DiagnosticMessage.messageData);
        }
    }    
}
