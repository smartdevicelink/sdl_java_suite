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
import java.util.List;

/**
 * This data is related to what a weather service would provide
 */
public class WeatherServiceData extends RPCStruct {

	public static final String KEY_LOCATION = "location";
	public static final String KEY_CURRENT_FORECAST = "currentForecast";
	public static final String KEY_MINUTE_FORECAST = "minuteForecast";
	public static final String KEY_HOURLY_FORECAST = "hourlyForecast";
	public static final String KEY_MULTIDAY_FORECAST = "multidayForecast";
	public static final String KEY_ALERTS = "alerts";

	// Constructors

	public WeatherServiceData() { }

	public WeatherServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public WeatherServiceData(@NonNull LocationDetails location) {
		this();
		setLocation(location);
	}

	// Setters and Getters

	/**
	 * @param location -
	 */
	public WeatherServiceData setLocation(@NonNull LocationDetails location) {
        setValue(KEY_LOCATION, location);
        return this;
    }

	/**
	 * @return location
	 */
	public LocationDetails getLocation() {
		return (LocationDetails) getObject(LocationDetails.class,KEY_LOCATION);
	}

	/**
	 * @param currentForecast -
	 */
	public WeatherServiceData setCurrentForecast( WeatherData currentForecast) {
        setValue(KEY_CURRENT_FORECAST, currentForecast);
        return this;
    }

	/**
	 * @return currentForecast
	 */
	public WeatherData getCurrentForecast() {
		return (WeatherData) getObject(WeatherData.class,KEY_CURRENT_FORECAST);
	}

	/**
	 * minsize: 15, maxsize: 60
	 * @param minuteForecast -
	 */
	public WeatherServiceData setMinuteForecast( List<WeatherData> minuteForecast) {
        setValue(KEY_MINUTE_FORECAST, minuteForecast);
        return this;
    }

	/**
	 * minsize: 15, maxsize: 60
	 * @return minuteForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getMinuteForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_MINUTE_FORECAST);
	}

	/**
	 * minsize: 1, maxsize: 96
	 * @param hourlyForecast -
	 */
	public WeatherServiceData setHourlyForecast( List<WeatherData> hourlyForecast) {
        setValue(KEY_HOURLY_FORECAST, hourlyForecast);
        return this;
    }

	/**
	 * minsize: 1, maxsize: 96
	 * @return hourlyForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getHourlyForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_HOURLY_FORECAST);
	}

	/**
	 * minsize: 1, maxsize: 30
	 * @param multidayForecast -
	 */
	public WeatherServiceData setMultidayForecast( List<WeatherData> multidayForecast) {
        setValue(KEY_MULTIDAY_FORECAST, multidayForecast);
        return this;
    }

	/**
	 * minsize: 1, maxsize: 30
	 * @return multidayForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getMultidayForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_MULTIDAY_FORECAST);
	}

	/**
	 * minsize: 1, maxsize: 10
	 * @param alerts -
	 */
	public WeatherServiceData setAlerts( List<WeatherAlert> alerts) {
        setValue(KEY_ALERTS, alerts);
        return this;
    }

	/**
	 * minsize: 1, maxsize: 10
	 * @return alerts
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherAlert> getAlerts(){
		return (List<WeatherAlert>) getObject(WeatherAlert.class,KEY_ALERTS);
	}

}
