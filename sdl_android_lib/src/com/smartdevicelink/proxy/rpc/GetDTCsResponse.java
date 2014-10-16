package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCResponse;

/**
 * Get DTCs Response is sent, when GetDTCs has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class GetDTCsResponse extends RPCResponse {
	public static final String dtc = "dtc";

    public GetDTCsResponse() {
        super("GetDTCs");
    }
    public GetDTCsResponse(Hashtable hash) {
        super(hash);
    }
    public List<String> getDtc() {
    	if(parameters.get(GetDTCsResponse.dtc) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(GetDTCsResponse.dtc);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof String){
        			return (List<String>) list;
    	}
    }
            }
        return null;
    }
    public void setDtc(List<String> dtc) {
        if (dtc != null) {
            parameters.put(GetDTCsResponse.dtc, dtc);
        } else {
        	parameters.remove(GetDTCsResponse.dtc);
        }
    }
}
