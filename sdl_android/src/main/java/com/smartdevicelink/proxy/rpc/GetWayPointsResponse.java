package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
		setParameters(KEY_WAY_POINTS, wayPoints);
    }
    @SuppressWarnings("unchecked")
    public List<LocationDetails> getWayPoints() {
		return (List<LocationDetails>) getObject(LocationDetails.class, KEY_WAY_POINTS);
    }
}
