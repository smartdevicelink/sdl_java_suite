package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.DIDResult;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDIDResponse extends RPCResponse {

    public ReadDIDResponse() {
        super("ReadDID");
    }
    public ReadDIDResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setDidResult(Vector<DIDResult> didResult) {
    	if (didResult != null) {
    		parameters.put(Names.didResult, didResult);
    	} else {
    		parameters.remove(Names.didResult);
    	}
    }
    @SuppressWarnings("unchecked")
    public Vector<DIDResult> getDidResult() {
        if (parameters.get(Names.didResult) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.didResult);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof DIDResult) {
	                return (Vector<DIDResult>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<DIDResult> newList = new Vector<DIDResult>();
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