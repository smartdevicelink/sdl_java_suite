package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Get DTCs Response is sent, when GetDTCs has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetDTCsResponse extends RPCResponse {

    public GetDTCsResponse() {
        super("GetDTCs");
    }
    public GetDTCsResponse(Hashtable hash) {
        super(hash);
    }
    public Vector<String> getDtc() {
    	if(parameters.get(Names.dtc) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)parameters.get(Names.dtc);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof String){
        			return (Vector<String>) list;
    	}
    }
            }
        return null;
    }
    public void setDtc(Vector<String> dtc) {
        if (dtc != null) {
            parameters.put(Names.dtc, dtc);
        } else {
        	parameters.remove(Names.dtc);
        }
    }
}