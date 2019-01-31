package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import java.util.Hashtable;
import java.util.List;

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

	/**
	 * Capabilities of app services including what service types are supported
	 * and the current state of services.
	 * @param servicesSupported - List<AppServiceType>
	 */
	public AppServicesCapabilities(@NonNull List<AppServiceType> servicesSupported){
		this();
		setServicesSupported(servicesSupported);

	}

	// Setters and Getters

	/**
	 * An array of supported app service types
	 * @param servicesSupported -
	 */
	public void setServicesSupported(List<AppServiceType> servicesSupported){
		setValue(KEY_SERVICES_SUPPORTED, servicesSupported);
	}

	/**
	 * An array of supported app service types
	 * @return servicesSupported
	 */
	@SuppressWarnings("unchecked")
	public List<AppServiceType> getServicesSupported(){
		return (List<AppServiceType>) getObject(AppServiceType.class,KEY_SERVICES_SUPPORTED);
	}

	/**
	 * An array of currently available services. If this is an update to the
	 * capability the affected services will include an update reason in that item
	 * @param appServices -
	 */
	public void setAppServices(List<AppServiceCapability> appServices){
		setValue(KEY_APP_SERVICES, appServices);
	}

	/**
	 * An array of currently available services. If this is an update to the
	 * capability the affected services will include an update reason in that item
	 * @return appServices
	 */
	@SuppressWarnings("unchecked")
	public List<AppServiceCapability> getAppServices(){
		return (List<AppServiceCapability>) getObject(AppServiceCapability.class,KEY_APP_SERVICES);
	}

}
