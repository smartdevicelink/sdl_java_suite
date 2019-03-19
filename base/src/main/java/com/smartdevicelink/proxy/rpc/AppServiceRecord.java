/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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

	public AppServiceRecord(@NonNull String serviceID, @NonNull AppServiceManifest serviceManifest,
							@NonNull Boolean servicePublished, @NonNull Boolean serviceActive) {
		this();
		setServiceID(serviceID);
		setServiceManifest(serviceManifest);
		setServicePublished(servicePublished);
		setServiceActive(serviceActive);
	}

	// Setters and Getters
	/**
	 * ID of this service
	 * @param serviceID - the service ID
	 */
	public void setServiceID(@NonNull String serviceID){
		setValue(KEY_SERVICE_ID, serviceID);
	}

	/**
	 * ID of this service
	 * @return serviceId
	 */
	public String getServiceID(){
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
