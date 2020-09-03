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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 Contains all the current data of the app service. The serviceType will link to which of the service data objects are
 included in this object (e.g. if the service type is MEDIA, the mediaServiceData param should be included).
 */
public class AppServiceData extends RPCStruct {

	public static final String KEY_SERVICE_TYPE = "serviceType";
	public static final String KEY_SERVICE_ID   = "serviceID";
	public static final String KEY_MEDIA_SERVICE_DATA = "mediaServiceData";
	public static final String KEY_WEATHER_SERVICE_DATA = "weatherServiceData";
	public static final String KEY_NAVIGATION_SERVICE_DATA = "navigationServiceData";


	// Constructors
	public AppServiceData() { }

	public AppServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public AppServiceData(@NonNull String serviceType, @NonNull String serviceId) {
		this();
		setServiceType(serviceType);
		setServiceID(serviceId);
	}

	// Setters and Getters

	/**
	 * @param serviceType -
	 */
	public AppServiceData setServiceType(@NonNull String serviceType) {
        setValue(KEY_SERVICE_TYPE, serviceType);
        return this;
    }

	/**
	 * @return serviceType -
	 */
	public String getServiceType() {
		return getString(KEY_SERVICE_TYPE);
	}

	/**
	 * @param serviceId -
	 */
	public AppServiceData setServiceID(@NonNull String serviceId) {
        setValue(KEY_SERVICE_ID, serviceId);
        return this;
    }

	/**
	 * @return serviceId -
	 */
	public String getServiceID() {
		return getString(KEY_SERVICE_ID);
	}

	/**
	 * @param mediaServiceData -
	 */
	public AppServiceData setMediaServiceData( MediaServiceData mediaServiceData) {
        setValue(KEY_MEDIA_SERVICE_DATA, mediaServiceData);
        return this;
    }

	/**
	 * @return mediaServiceData -
	 */
	public MediaServiceData getMediaServiceData() {
		return (MediaServiceData) getObject(MediaServiceData.class,KEY_MEDIA_SERVICE_DATA);
	}

	/**
	 * @param weatherServiceData -
	 */
	public AppServiceData setWeatherServiceData( WeatherServiceData weatherServiceData) {
        setValue(KEY_WEATHER_SERVICE_DATA, weatherServiceData);
        return this;
    }

	/**
	 * @return weatherServiceData -
	 */
	public WeatherServiceData getWeatherServiceData() {
		return (WeatherServiceData) getObject(WeatherServiceData.class,KEY_WEATHER_SERVICE_DATA);
	}

	/**
	 * @param navigationServiceData -
	 */
	public AppServiceData setNavigationServiceData( NavigationServiceData navigationServiceData) {
        setValue(KEY_NAVIGATION_SERVICE_DATA, navigationServiceData);
        return this;
    }

	/**
	 * @return navigationServiceData -
	 */
	public NavigationServiceData getNavigationServiceData() {
		return (NavigationServiceData) getObject(NavigationServiceData.class, KEY_NAVIGATION_SERVICE_DATA);
	}

}
