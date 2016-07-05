package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDIDResponse extends RPCResponse {
	public static final String KEY_DID_RESULT = "didResult";

    public ReadDIDResponse() {
        super(FunctionID.READ_DID.toString());
    }
    public ReadDIDResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setDidResult(List<DIDResult> didResult) {
    	if (didResult != null) {
    		parameters.put(KEY_DID_RESULT, didResult);
    	} else {
    		parameters.remove(KEY_DID_RESULT);
    	}
    }
    @SuppressWarnings("unchecked")
    public List<DIDResult> getDidResult() {
        if (parameters.get(KEY_DID_RESULT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_DID_RESULT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof DIDResult) {
	                return (List<DIDResult>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<DIDResult> newList = new ArrayList<DIDResult>();
	                for (Object hashObj : list) {
	                    newList.add(new DIDResult((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
