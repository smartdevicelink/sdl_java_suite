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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class WeatherServiceManifest extends RPCStruct {

    public static final String KEY_CURRENT_FORECAST_SUPPORTED = "currentForecastSupported";
    public static final String KEY_MAX_MULTIDAY_FORECAST_AMOUNT = "maxMultidayForecastAmount";
    public static final String KEY_MAX_HOURLY_FORECAST_AMOUNT = "maxHourlyForecastAmount";
    public static final String KEY_MAX_MINUTELY_FORECAST_AMOUNT = "maxMinutelyForecastAmount";
    public static final String KEY_WEATHER_FOR_LOCATION_SUPPORTED = "weatherForLocationSupported";

    // Constructors

    public WeatherServiceManifest() {
    }

    public WeatherServiceManifest(Hashtable<String, Object> hash) {
        super(hash);
    }

    // Setters and Getters

    /**
     * Set whether the current forecast is supported
     *
     * @param currentForecastSupported -
     */
    public WeatherServiceManifest setCurrentForecastSupported(Boolean currentForecastSupported) {
        setValue(KEY_CURRENT_FORECAST_SUPPORTED, currentForecastSupported);
        return this;
    }

    /**
     * Get whether the current forecast is supported
     *
     * @return currentForecastSupported
     */
    public Boolean getCurrentForecastSupported() {
        return getBoolean(KEY_CURRENT_FORECAST_SUPPORTED);
    }

    /**
     * Set the max multi day forecast amount
     *
     * @param maxMultidayForecastAmount -
     */
    public WeatherServiceManifest setMaxMultidayForecastAmount(Integer maxMultidayForecastAmount) {
        setValue(KEY_MAX_MULTIDAY_FORECAST_AMOUNT, maxMultidayForecastAmount);
        return this;
    }

    /**
     * Get the max multi day forecast amount
     *
     * @return maxMultidayForecastAmount
     */
    public Integer getMaxMultidayForecastAmount() {
        return getInteger(KEY_MAX_MULTIDAY_FORECAST_AMOUNT);
    }

    /**
     * Set the max hourly forecast amount
     *
     * @param maxHourlyForecastAmount -
     */
    public WeatherServiceManifest setMaxHourlyForecastAmount(Integer maxHourlyForecastAmount) {
        setValue(KEY_MAX_HOURLY_FORECAST_AMOUNT, maxHourlyForecastAmount);
        return this;
    }

    /**
     * Get the max hourly forecast amount
     *
     * @return maxHourlyForecastAmount
     */
    public Integer getMaxHourlyForecastAmount() {
        return getInteger(KEY_MAX_HOURLY_FORECAST_AMOUNT);
    }

    /**
     * Set the max minutely forecast amount
     *
     * @param maxMinutelyForecastAmount -
     */
    public WeatherServiceManifest setMaxMinutelyForecastAmount(Integer maxMinutelyForecastAmount) {
        setValue(KEY_MAX_MINUTELY_FORECAST_AMOUNT, maxMinutelyForecastAmount);
        return this;
    }

    /**
     * Get the max minutely forecast amount
     *
     * @return maxMinutelyForecastAmount
     */
    public Integer getMaxMinutelyForecastAmount() {
        return getInteger(KEY_MAX_MINUTELY_FORECAST_AMOUNT);
    }

    /**
     * Set whether the weather is supported for the current location
     *
     * @param weatherForLocationSupported -
     */
    public WeatherServiceManifest setWeatherForLocationSupported(Boolean weatherForLocationSupported) {
        setValue(KEY_WEATHER_FOR_LOCATION_SUPPORTED, weatherForLocationSupported);
        return this;
    }

    /**
     * Get whether the weather is supported for the current location
     *
     * @return weatherForLocationSupported
     */
    public Boolean getWeatherForLocationSupported() {
        return getBoolean(KEY_WEATHER_FOR_LOCATION_SUPPORTED);
    }
}
