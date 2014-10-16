package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;
import java.util.List;

public class SystemRequest extends RPCRequest {
	public static final String fileName = "fileName";
	public static final String requestType = "requestType";
	public static final String data = "data";
    public SystemRequest() {
        super("SystemRequest");
    }

	public SystemRequest(boolean bLegacy) {
        super("EncodedSyncPData");
    }
    
    public SystemRequest(Hashtable hash) {
        super(hash);
    }
    
    public List<String> getLegacyData() {
        if (parameters.get(SystemRequest.data) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(SystemRequest.data);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (List<String>) list;
        		}
        	}
        }
    	return null;
    }
 
    public void setLegacyData( List<String> data ) {
    	if ( data!= null) {
    		parameters.put(SystemRequest.data, data );
    	}
    }    
            
    public String getFileName() {
        return (String) parameters.get(SystemRequest.fileName);
    }
    
    public void setFileName(String fileName) {
        if (fileName != null) {
            parameters.put(SystemRequest.fileName, fileName);
        } else {
        	parameters.remove(SystemRequest.fileName);
        }
    }    

    public RequestType getRequestType() {
        Object obj = parameters.get(SystemRequest.requestType);
        if (obj instanceof RequestType) {
            return (RequestType) obj;
        } else if (obj instanceof String) {
            RequestType theCode = null;
            try {
                theCode = RequestType.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError(
                        "Failed to parse " + getClass().getSimpleName() + "." +
                        		SystemRequest.requestType, e);
            }
            return theCode;
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(SystemRequest.requestType, requestType);
        } else {
            parameters.remove(SystemRequest.requestType);
        }
    }
}
