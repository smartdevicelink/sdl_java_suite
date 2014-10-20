package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

public class DiagnosticMessage extends RPCRequest {

    public DiagnosticMessage() {
        super("DiagnosticMessage");
    }

    public DiagnosticMessage(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public void setTargetID(Integer targetID) {
    	if (targetID != null) {
    		parameters.put(Names.targetID, targetID);
    	} else {
    		parameters.remove(Names.targetID);
    	}
    }
    public Integer getTargetID() {
    	return (Integer) parameters.get(Names.targetID);
    }    

    public void setMessageLength(Integer messageLength) {
    	if (messageLength != null) {
    		parameters.put(Names.messageLength, messageLength);
    	} else {
    		parameters.remove(Names.messageLength);
    	}
    }
    public Integer getMessageLength() {
    	return (Integer) parameters.get(Names.messageLength);
    }

    @SuppressWarnings("unchecked")
    public Vector<Integer> getMessageData() {
    	if(parameters.get(Names.messageData) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)parameters.get(Names.messageData);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (Vector<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    
    public void setMessageData(Vector<Integer> messageData) {
        if (messageData != null) {
            parameters.put(Names.messageData, messageData);
        } else {
        	parameters.remove(Names.messageData);
        }
    }    
}
