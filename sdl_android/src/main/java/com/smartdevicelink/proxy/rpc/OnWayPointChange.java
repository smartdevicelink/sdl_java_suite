package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.ArrayList;
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
		if (parameters.get(KEY_WAY_POINTS) instanceof List<?>) {
			List<?> list = (List<?>)parameters.get(KEY_WAY_POINTS);
			if (list != null && list.size()>0) {
				Object obj = list.get(0);
				if(obj instanceof LocationDetails){
					return (List<LocationDetails>) list;
				} else if(obj instanceof Hashtable) {
					List<LocationDetails> newList = new ArrayList<LocationDetails>();
					for (Object hash:list) {
						newList.add(new LocationDetails((Hashtable<String, Object>)hash));
					}
					return newList;
				}
			}
		}
		return null;
	}

	public void setWayPoints(List<LocationDetails> wayPoints) {
		if (wayPoints != null) {
			parameters.put(KEY_WAY_POINTS, wayPoints);
		} else {
			parameters.remove(KEY_WAY_POINTS);
        }
	}
}
