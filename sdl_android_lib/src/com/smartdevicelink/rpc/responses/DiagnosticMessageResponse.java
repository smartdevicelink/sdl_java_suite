package com.smartdevicelink.rpc.responses;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

public class DiagnosticMessageResponse extends RpcResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";

    public DiagnosticMessageResponse() {
        super(FunctionId.DIAGNOSTIC_MESSAGE.toString());
    }
    public DiagnosticMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    @SuppressWarnings("unchecked")
    public List<Integer> getMessageDataResult() {
    	if(parameters.get(KEY_MESSAGE_DATA_RESULT) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(KEY_MESSAGE_DATA_RESULT);
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
            parameters.put(KEY_MESSAGE_DATA_RESULT, messageDataResult);
        } else {
        	parameters.remove(KEY_MESSAGE_DATA_RESULT);
        }
    }
}
