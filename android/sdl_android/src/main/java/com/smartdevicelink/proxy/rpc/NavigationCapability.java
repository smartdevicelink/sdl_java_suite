package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/*
 * Extended capabilities for an onboard navigation system
 */
public class NavigationCapability extends RPCStruct{
	public static final String KEY_LOCATION_ENABLED = "sendLocationEnabled";
	public static final String KEY_GETWAYPOINTS_ENABLED = "getWayPointsEnabled";

	public NavigationCapability(){}

	public NavigationCapability(Hashtable<String, Object> hash) {
		super(hash);
	}

	public Boolean getSendLocationEnabled(){
		return getBoolean(KEY_LOCATION_ENABLED);
	}

	public void setSendLocationEnabled(Boolean sendLocationEnabled){
		setValue(KEY_LOCATION_ENABLED, sendLocationEnabled);
	}

	public Boolean getWayPointsEnabled(){
		return getBoolean(KEY_GETWAYPOINTS_ENABLED);
	}

	public void setWayPointsEnabled(Boolean getWayPointsEnabled){
		setValue(KEY_GETWAYPOINTS_ENABLED, getWayPointsEnabled);
	}
}
