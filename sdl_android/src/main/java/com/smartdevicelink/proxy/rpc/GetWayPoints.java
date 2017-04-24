package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.WayPointType;

import java.util.Hashtable;

public class GetWayPoints extends RPCRequest {
	public static final String KEY_WAY_POINT_TYPE = "wayPointType";

    public GetWayPoints() {
        super(FunctionID.GET_WAY_POINTS.toString());
    }

    public GetWayPoints(Hashtable<String, Object> hash) {
        super(hash);
    }

    public WayPointType getWayPointType() {
        Object obj = parameters.get(KEY_WAY_POINT_TYPE);
        if (obj instanceof WayPointType) {
            return (WayPointType) obj;
        } else if (obj instanceof String) {
            return WayPointType.valueForString((String) obj);
        }
        return null;
    }

    public void setWayPointType(WayPointType wayPointType) {
        if (wayPointType != null) {
            parameters.put(KEY_WAY_POINT_TYPE, wayPointType);
        } else {
            parameters.remove(KEY_WAY_POINT_TYPE);
        }
    }
}
