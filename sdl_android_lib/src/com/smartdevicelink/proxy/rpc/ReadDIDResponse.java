package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcResponse;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDidResponse extends RpcResponse {
	public static final String KEY_DID_RESULT = "didResult";

    public ReadDidResponse() {
        super(FunctionId.READ_DID.toString());
    }
    public ReadDidResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setDidResult(List<DidResult> didResult) {
    	if (didResult != null) {
    		parameters.put(KEY_DID_RESULT, didResult);
    	} else {
    		parameters.remove(KEY_DID_RESULT);
    	}
    }
    @SuppressWarnings("unchecked")
    public List<DidResult> getDidResult() {
        if (parameters.get(KEY_DID_RESULT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_DID_RESULT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof DidResult) {
	                return (List<DidResult>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<DidResult> newList = new ArrayList<DidResult>();
	                for (Object hashObj : list) {
	                    newList.add(new DidResult((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
