package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;

public class DiagnosticMessageResponse extends RPCResponse {
	public static final String messageDataResult = "messageDataResult";

    public DiagnosticMessageResponse() {
        super("DiagnosticMessage");
    }
    public DiagnosticMessageResponse(Hashtable hash) {
        super(hash);
    }
    public Vector<Integer> getMessageDataResult() {
    	if(parameters.get(DiagnosticMessageResponse.messageDataResult) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)parameters.get(DiagnosticMessageResponse.messageDataResult);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (Vector<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    
    public void setMessageDataResult(Vector<Integer> messageDataResult) {
        if (messageDataResult != null) {
            parameters.put(DiagnosticMessageResponse.messageDataResult, messageDataResult);
        } else {
        	parameters.remove(DiagnosticMessageResponse.messageDataResult);
        }
    }
}
