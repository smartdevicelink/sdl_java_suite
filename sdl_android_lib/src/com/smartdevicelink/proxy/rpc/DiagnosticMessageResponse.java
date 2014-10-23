package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

public class DiagnosticMessageResponse extends RPCResponse {

    public DiagnosticMessageResponse() {
        super("DiagnosticMessage");
    }
    public DiagnosticMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    @SuppressWarnings("unchecked")
    public Vector<Integer> getMessageDataResult() {
    	if(parameters.get(Names.messageDataResult) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)parameters.get(Names.messageDataResult);
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
            parameters.put(Names.messageDataResult, messageDataResult);
        } else {
        	parameters.remove(Names.messageDataResult);
        }
    }
}
