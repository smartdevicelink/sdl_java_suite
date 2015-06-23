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

    	boolean valid = true;
    	
    	for ( DIDResult item : didResult ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (didResult != null) && (didResult.size() > 0) && valid) {
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

	        	List<DIDResult> didResultList  = new ArrayList<DIDResult>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw DIDResult and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof DIDResult) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			didResultList.add(new DIDResult((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<DIDResult>) list;
	        	} else if (flagHash) {
	        		return didResultList;
	        	}
	        }
        }
        return null;
    }
}
