package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class GetWayPointsResponse extends RPCResponse {
	public static final String KEY_WAY_POINTS = "wayPoints";

    public GetWayPointsResponse() {
        super(FunctionID.GET_WAY_POINTS.toString());
    }
    public GetWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setWayPoints(List<LocationDetails> wayPoints) {
    	if (wayPoints != null) {
    		parameters.put(KEY_WAY_POINTS, wayPoints);
    	} else {
    		parameters.remove(KEY_WAY_POINTS);
    	}
    }
    @SuppressWarnings("unchecked")
    public List<LocationDetails> getWayPoints() {
        if (parameters.get(KEY_WAY_POINTS) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_WAY_POINTS);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof LocationDetails) {
	                return (List<LocationDetails>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<LocationDetails> newList = new ArrayList<LocationDetails>();
	                for (Object hashObj : list) {
	                    newList.add(new LocationDetails((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
}
