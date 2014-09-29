package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.Vector;

public class SystemRequest extends RPCRequest {
    public SystemRequest() {
        super("SystemRequest");
    }

	public SystemRequest(boolean bLegacy) {
        super("EncodedSyncPData");
    }
    
    public SystemRequest(Hashtable hash) {
        super(hash);
    }
    
    public Vector<String> getLegacyData() {
        if (parameters.get(Names.data) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)parameters.get(Names.data);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (Vector<String>) list;
        		}
        	}
        }
    	return null;
    }
 
    public void setLegacyData( Vector<String> data ) {
    	if ( data!= null) {
    		parameters.put(Names.data, data );
    	}
    }    
            
    public String getFileName() {
        return (String) parameters.get(Names.fileName);
    }
    
    public void setFileName(String fileName) {
        if (fileName != null) {
            parameters.put(Names.fileName, fileName);
        } else {
        	parameters.remove(Names.fileName);
        }
    }    

    public RequestType getRequestType() {
        Object obj = parameters.get(Names.requestType);
        if (obj instanceof RequestType) {
            return (RequestType) obj;
        } else if (obj instanceof String) {
            RequestType theCode = null;
            try {
                theCode = RequestType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		Names.requestType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(Names.requestType, requestType);
        } else {
            parameters.remove(Names.requestType);
        }
    }
}
