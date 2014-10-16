package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCResponse;

public class DiagnosticMessageResponse extends RPCResponse {
	public static final String messageDataResult = "messageDataResult";

    public DiagnosticMessageResponse() {
        super("DiagnosticMessage");
    }
    public DiagnosticMessageResponse(Hashtable hash) {
        super(hash);
    }
    public List<Integer> getMessageDataResult() {
    	if(parameters.get(DiagnosticMessageResponse.messageDataResult) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(DiagnosticMessageResponse.messageDataResult);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (List<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    
    public void setMessageDataResult(List<Integer> messageDataResult) {
        if (messageDataResult != null) {
            parameters.put(DiagnosticMessageResponse.messageDataResult, messageDataResult);
        } else {
        	parameters.remove(DiagnosticMessageResponse.messageDataResult);
        }
    }
}
