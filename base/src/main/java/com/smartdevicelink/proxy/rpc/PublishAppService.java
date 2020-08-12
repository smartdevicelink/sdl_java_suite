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

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * Registers a service offered by this app on the module.
 * Subsequent calls with the same service type will update the manifest for that service.
 */
public class PublishAppService extends RPCRequest {

	public static final String KEY_APP_SERVICE_MANIFEST = "appServiceManifest";

	// Constructors
	/**
	 * Constructs a new PublishAppService object
	 */
	public PublishAppService() {
		super(FunctionID.PUBLISH_APP_SERVICE.toString());
	}

	/**
	 * Constructs a new PublishAppService object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public PublishAppService(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a new PublishAppService object
	 * @param appServiceManifest - The appServiceManifest
	 */
	public PublishAppService(@NonNull AppServiceManifest appServiceManifest) {
			this();
			setAppServiceManifest(appServiceManifest);
	}

	// Getters / Setters

	/**
	 * The manifest of the service that wishes to be published.
	 * If already published, the updated manifest for this service.
	 * @param serviceManifest - the App Service Manifest
	 */
	public void setAppServiceManifest(@NonNull AppServiceManifest serviceManifest){
		setParameters(KEY_APP_SERVICE_MANIFEST, serviceManifest);
	}

	/**
	 * The manifest of the service that wishes to be published.
	 * If already published, the updated manifest for this service.
	 * @return serviceManifest - the App Service Manifest
	 */
	public AppServiceManifest getAppServiceManifest(){
		return (AppServiceManifest) getObject(AppServiceManifest.class,KEY_APP_SERVICE_MANIFEST);
	}
}
