package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;
import java.util.List;

public class OnWayPointChange extends RPCNotification {
	public static final String KEY_WAY_POINTS = "wayPoints";

	public OnWayPointChange() {
		super(FunctionID.ON_WAY_POINT_CHANGE.toString());
	}

	public OnWayPointChange(Hashtable<String, Object> hash) {
		super(hash);
	}

    @SuppressWarnings("unchecked")
	public List<LocationDetails> getWayPoints() {
		return (List<LocationDetails>) getObject(LocationDetails.class, KEY_WAY_POINTS);
	}

	public void setWayPoints(List<LocationDetails> wayPoints) {
		setParameters(KEY_WAY_POINTS, wayPoints);
	}
}
