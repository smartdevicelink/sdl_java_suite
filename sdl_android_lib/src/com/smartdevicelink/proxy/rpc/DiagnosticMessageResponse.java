package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

public class DiagnosticMessageResponse extends RPCResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";

    public DiagnosticMessageResponse() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }
    public DiagnosticMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    @SuppressWarnings("unchecked")
    public List<Integer> getMessageDataResult() {
    	if(parameters.get(KEY_MESSAGE_DATA_RESULT) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(KEY_MESSAGE_DATA_RESULT);
    		if(list != null && list.size()>0){
    			for( Object obj : list ) {
        			if (!(obj instanceof Integer)) {
        				return null;
        			}
        		}
        		return (List<Integer>) list;
    		}
    	}
        return null;
    }
    
    public void setMessageDataResult(List<Integer> messageDataResult) {

    	boolean valid = true;
    	
    	for ( Integer item : messageDataResult ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (messageDataResult != null) && (messageDataResult.size() > 0) && valid) {
            parameters.put(KEY_MESSAGE_DATA_RESULT, messageDataResult);
        } else {
        	parameters.remove(KEY_MESSAGE_DATA_RESULT);
        }
    }
}
