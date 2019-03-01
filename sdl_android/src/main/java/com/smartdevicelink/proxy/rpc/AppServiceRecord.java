package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 *  This manifest contains all the information necessary for the
 *  service to be published, activated, and consumers able to interact with it
 */
public class AppServiceRecord extends RPCStruct {

	public static final String KEY_SERVICE_ID = "serviceID";
	public static final String KEY_SERVICE_MANIFEST = "serviceManifest";
	public static final String KEY_SERVICE_PUBLISHED = "servicePublished";
	public static final String KEY_SERVICE_ACTIVE = "serviceActive";


	// Constructors
	public AppServiceRecord() { }

	public AppServiceRecord(Hashtable<String, Object> hash) {
		super(hash);
	}

	public AppServiceRecord(@NonNull String serviceId, @NonNull AppServiceManifest serviceManifest,
							@NonNull Boolean servicePublished, @NonNull Boolean serviceActive) {
		this();
		setServiceId(serviceId);
		setServiceManifest(serviceManifest);
		setServicePublished(servicePublished);
		setServiceActive(serviceActive);
	}

	// Setters and Getters
	/**
	 * ID of this service
	 * @param serviceId - the service ID
	 */
	public void setServiceId(@NonNull String serviceId){
		setValue(KEY_SERVICE_ID, serviceId);
	}

	/**
	 * ID of this service
	 * @return serviceId
	 */
	public String getServiceId(){
		return getString(KEY_SERVICE_ID);
	}

	/**
	 * the App Service Manifest
	 * @param serviceManifest - the App Service Manifest
	 */
	public void setServiceManifest(@NonNull AppServiceManifest serviceManifest){
		setValue(KEY_SERVICE_MANIFEST, serviceManifest);
	}

	/**
	 * the App Service Manifest
	 * @return serviceManifest - the App Service Manifest
	 */
	public AppServiceManifest getServiceManifest(){
		return (AppServiceManifest) getObject(AppServiceManifest.class,KEY_SERVICE_MANIFEST);
	}

	/**
	 * If true, the service is published and available. If false, the service has likely just been
	 * unpublished, and should be considered unavailable.
	 * @param servicePublished - boolean
	 */
	public void setServicePublished(@NonNull Boolean servicePublished){
		setValue(KEY_SERVICE_PUBLISHED, servicePublished);
	}

	/**
	 * If true, the service is published and available. If false, the service has likely just been
	 * unpublished, and should be considered unavailable.
	 * @return  servicePublished - boolean
	 */
	public Boolean getServicePublished(){
		return getBoolean(KEY_SERVICE_PUBLISHED);
	}

	/**
	 * If true, the service is the active primary service of the supplied service type. It will receive
	 * all potential RPCs that are passed through to that service type. If false, it is not the primary
	 * service of the supplied type. See servicePublished for its availability.
	 * @param serviceActive - boolean
	 */
	public void setServiceActive(@NonNull Boolean serviceActive){
		setValue(KEY_SERVICE_ACTIVE, serviceActive);
	}

	/**
	 * If true, the service is the active primary service of the supplied service type. It will receive
	 * all potential RPCs that are passed through to that service type. If false, it is not the primary
	 * service of the supplied type. See servicePublished for its availability.
	 * @return  serviceActive - boolean
	 */
	public Boolean getServiceActive(){
		return getBoolean(KEY_SERVICE_ACTIVE);
	}

}
