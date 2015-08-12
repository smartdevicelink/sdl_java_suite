package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
/**
 * Diagnostic Message Response is sent, when DiagnosticMessage has been called.
 * 
 * @since SmartDeviceLink 3.0
 */
public class DiagnosticMessageResponse extends RPCResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";
	/** 
	 * Constructs a new DiagnosticMessageResponse object
	 */

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
