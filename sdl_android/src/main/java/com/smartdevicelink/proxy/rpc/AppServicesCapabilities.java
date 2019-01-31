package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Capabilities of app services including what service types are supported
 * and the current state of services.
 */
public class AppServicesCapabilities extends RPCStruct {

	public static final String KEY_SERVICES_SUPPORTED = "servicesSupported";
	public static final String KEY_APP_SERVICES = "appServices";

	// Constructors

	/**
	 * Capabilities of app services including what service types are supported
	 * and the current state of services.
	 */
	public AppServicesCapabilities(){}

	/**
	 * Capabilities of app services including what service types are supported
	 * and the current state of services.
	 * @param hash of parameters
	 */
	public AppServicesCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	// Setters and Getters



}
