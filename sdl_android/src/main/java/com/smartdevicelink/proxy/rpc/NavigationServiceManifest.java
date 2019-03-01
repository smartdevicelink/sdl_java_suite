package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class NavigationServiceManifest extends RPCStruct {

	public static final String KEY_ACCEPTS_WAY_POINTS = "acceptsWayPoints";

	// Constructors

	public NavigationServiceManifest() { }

	public NavigationServiceManifest(Hashtable<String, Object> hash) {
		super(hash);
	}

	// Setters and Getters

	/**
	 * Informs the subscriber if this service can actually accept way points.
	 * @param acceptsWayPoints -
	 */
	public void setAcceptsWayPoints(Boolean acceptsWayPoints){
		setValue(KEY_ACCEPTS_WAY_POINTS, acceptsWayPoints);
	}

	/**
	 * Informs the subscriber if this service can actually accept way points.
	 * @return acceptsWayPoints
	 */
	public Boolean getAcceptsWayPoints(){
		return getBoolean(KEY_ACCEPTS_WAY_POINTS);
	}

}
