package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.DIDResult;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDIDResponse extends RPCResponse {
	public static final String didResult = "didResult";

    public ReadDIDResponse() {
        super("ReadDID");
    }
    public ReadDIDResponse(Hashtable hash) {
        super(hash);
    }
    public void setDidResult(Vector<DIDResult> didResult) {
    	if (didResult != null) {
    		parameters.put(ReadDIDResponse.didResult, didResult);
    	} else {
    		parameters.remove(ReadDIDResponse.didResult);
    	}
    }
    public Vector<DIDResult> getDidResult() {
        if (parameters.get(ReadDIDResponse.didResult) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(ReadDIDResponse.didResult);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof DIDResult) {
	                return (Vector<DIDResult>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<DIDResult> newList = new Vector<DIDResult>();
	                for (Object hashObj : list) {
	                    newList.add(new DIDResult((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
