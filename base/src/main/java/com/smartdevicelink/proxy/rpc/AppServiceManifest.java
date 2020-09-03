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
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *  This manifest contains all the information necessary for the service to be
 *  published, activated, and allow consumers to interact with it
 */
public class AppServiceManifest extends RPCStruct {

	public static final String KEY_SERVICE_NAME = "serviceName";
	public static final String KEY_SERVICE_TYPE = "serviceType";
	public static final String KEY_SERVICE_ICON = "serviceIcon";
	public static final String KEY_ALLOW_APP_CONSUMERS = "allowAppConsumers";
	public static final String KEY_RPC_SPEC_VERSION = "rpcSpecVersion";
	public static final String KEY_HANDLED_RPCS = "handledRPCs";
	public static final String KEY_MEDIA_SERVICE_MANIFEST = "mediaServiceManifest";
	public static final String KEY_WEATHER_SERVICE_MANIFEST = "weatherServiceManifest";
	public static final String KEY_NAVIGATION_SERVICE_MANIFEST = "navigationServiceManifest";

	// Constructors
	public AppServiceManifest() { }

	public AppServiceManifest(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructor that takes in the mandatory parameters.
	 * @param serviceType the type of service this is, use {@link com.smartdevicelink.proxy.rpc.enums.AppServiceType}
	 * @see com.smartdevicelink.proxy.rpc.enums.AppServiceType
	 */
	public AppServiceManifest(@NonNull String serviceType) {
		this();
		setServiceType(serviceType);
	}
	/**
	 * Constructor that takes in the mandatory parameters.
	 * @param serviceType the type of service this is
	 * @see com.smartdevicelink.proxy.rpc.enums.AppServiceType
	 */
	public AppServiceManifest(@NonNull AppServiceType serviceType) {
		this();
		setServiceType(serviceType.name());
	}

	// Setters and Getters
	/**
	 * Unique name of this service
	 * @param serviceName - the service name
	 */
	public AppServiceManifest setServiceName( String serviceName) {
        setValue(KEY_SERVICE_NAME, serviceName);
        return this;
    }

	/**
	 * Unique name of this service
	 * @return ServiceName
	 */
	public String getServiceName(){
		return getString(KEY_SERVICE_NAME);
	}

	/**
	 * The type of service that is to be offered by this app
	 * @param serviceType - the serviceType use {@link AppServiceType}
	 * @see AppServiceType
	 */
	public AppServiceManifest setServiceType(@NonNull String serviceType) {
        setValue(KEY_SERVICE_TYPE, serviceType);
        return this;
    }

	/**
	 * The type of service that is to be offered by this app
	 * @return the AppServiceType
	 * @see com.smartdevicelink.proxy.rpc.enums.AppServiceType
	 */
	public String getServiceType(){
		return getString(KEY_SERVICE_TYPE);
	}

	/**
	 * The icon to be associated with this service Most likely the same as the appIcon.
	 * @param serviceIcon - The Service Icon Image
	 */
	public AppServiceManifest setServiceIcon( Image serviceIcon) {
        setValue(KEY_SERVICE_ICON, serviceIcon);
        return this;
    }

	/**
	 * The icon to be associated with this service Most likely the same as the appIcon.
	 * @return serviceIcon Image
	 */
	public Image getServiceIcon(){
		return (Image) getObject(Image.class, KEY_SERVICE_ICON);
	}

	/**
	 * If true, app service consumers beyond the IVI system will be able to access this service. If false,
	 * only the IVI system will be able consume the service. If not provided, it is assumed to be false.
	 * @param allowAppConsumers - boolean
	 */
	public AppServiceManifest setAllowAppConsumers( Boolean allowAppConsumers) {
        setValue(KEY_ALLOW_APP_CONSUMERS, allowAppConsumers);
        return this;
    }

	/**
	 * If true, app service consumers beyond the IVI system will be able to access this service. If false,
	 * only the IVI system will be able consume the service. If not provided, it is assumed to be false.
	 * @return allowAppConsumers - boolean
	 */
	public Boolean getAllowAppConsumers(){
		return getBoolean(KEY_ALLOW_APP_CONSUMERS);
	}

	/**
	 * This is the max RPC Spec version the app service understands. This is important during the RPC pass through functionality.
	 * If not included, it is assumed the max version of the module is acceptable.
	 * @param rpcSpecVersion - The rpcSpecVersion
	 */
	public AppServiceManifest setRpcSpecVersion( SdlMsgVersion rpcSpecVersion) {
        setValue(KEY_RPC_SPEC_VERSION, rpcSpecVersion);
        return this;
    }

	/**
	 * This is the max RPC Spec version the app service understands. This is important during the RPC pass through functionality.
	 * If not included, it is assumed the max version of the module is acceptable.
	 * @return rpcSpecVersion - The rpcSpecVersion
	 */
	public SdlMsgVersion getRpcSpecVersion(){
		return (SdlMsgVersion) getObject(SdlMsgVersion.class,KEY_RPC_SPEC_VERSION);
	}

	/**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @param handledRPCs - The List of Handled RPCs using their ID value from the FunctionID enum
	 * @see FunctionID
	 * @see #setHandledRpcsUsingFunctionIDs( List )
	 */
	public AppServiceManifest setHandledRpcs( List<Integer> handledRPCs) {
        setValue(KEY_HANDLED_RPCS, handledRPCs);
        return this;
    }
	/**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @param handledRPCs - The List of Handled RPCs using the FunctionID enum
	 * @see #setHandledRpcs( List )
	 */
	public AppServiceManifest setHandledRpcsUsingFunctionIDs( List<FunctionID> handledRPCs) {
        if(handledRPCs != null){
			List<Integer> rpcIds = new ArrayList<>();
			for(FunctionID functionID : handledRPCs){
				rpcIds.add(functionID.getId());
			}
			setHandledRpcs(rpcIds);
		}else{
			setValue(KEY_HANDLED_RPCS, null);
		}
        return this;
    }

	/**
	 * This field contains the FunctionID integer ID values for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @return handledRPCs - The List of Handled RPC IDs obtained through the FunctionID enum
	 * @see com.smartdevicelink.protocol.enums.FunctionID
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getHandledRpcs(){
		return (List<Integer>) getObject(Integer.class,KEY_HANDLED_RPCS);
	}

	/**
	 * The MediaServiceManifest
	 * @param mediaServiceManifest - The mediaServiceManifest
	 */
	public AppServiceManifest setMediaServiceManifest( MediaServiceManifest mediaServiceManifest) {
        setValue(KEY_MEDIA_SERVICE_MANIFEST, mediaServiceManifest);
        return this;
    }

	/**
	 * The MediaServiceManifest
	 * @return mediaServiceManifest - The mediaServiceManifest
	 */
	public MediaServiceManifest getMediaServiceManifest(){
		return (MediaServiceManifest) getObject(MediaServiceManifest.class,KEY_MEDIA_SERVICE_MANIFEST);
	}

	/**
	 * The WeatherServiceManifest
	 * @param weatherServiceManifest - The weatherServiceManifest
	 */
	public AppServiceManifest setWeatherServiceManifest( WeatherServiceManifest weatherServiceManifest) {
        setValue(KEY_WEATHER_SERVICE_MANIFEST, weatherServiceManifest);
        return this;
    }

	/**
	 * The WeatherServiceManifest
	 * @return weatherServiceManifest - The weatherServiceManifest
	 */
	public WeatherServiceManifest getWeatherServiceManifest(){
		return (WeatherServiceManifest) getObject(WeatherServiceManifest.class,KEY_WEATHER_SERVICE_MANIFEST);
	}

	/**
	 * The NavigationServiceManifest
	 * @param navigationServiceManifest - The navigationServiceManifest
	 */
	public AppServiceManifest setNavigationServiceManifest( NavigationServiceManifest navigationServiceManifest) {
        setValue(KEY_NAVIGATION_SERVICE_MANIFEST, navigationServiceManifest);
        return this;
    }

	/**
	 * The NavigationServiceManifest
	 * @return navigationServiceManifest - The navigationServiceManifest
	 */
	public NavigationServiceManifest getNavigationServiceManifest(){
		return (NavigationServiceManifest) getObject(NavigationServiceManifest.class,KEY_NAVIGATION_SERVICE_MANIFEST);
	}
}
