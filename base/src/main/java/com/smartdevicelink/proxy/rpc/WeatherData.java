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

public class WeatherData extends RPCStruct {

	public static final String KEY_CURRENT_TEMPERATURE = "currentTemperature";
	public static final String KEY_TEMPERATURE_HIGH = "temperatureHigh";
	public static final String KEY_TEMPERATURE_LOW = "temperatureLow";
	public static final String KEY_APPARENT_TEMPERATURE = "apparentTemperature";
	public static final String KEY_APPARENT_TEMPERATURE_HIGH = "apparentTemperatureHigh";
	public static final String KEY_APPARENT_TEMPERATURE_LOW = "apparentTemperatureLow";
	public static final String KEY_WEATHER_SUMMARY = "weatherSummary";
	public static final String KEY_TIME = "time";
	public static final String KEY_HUMIDITY = "humidity";
	public static final String KEY_CLOUD_COVER = "cloudCover";
	public static final String KEY_MOON_PHASE = "moonPhase";
	public static final String KEY_WIND_BEARING = "windBearing";
	public static final String KEY_WIND_GUST = "windGust";
	public static final String KEY_WIND_SPEED = "windSpeed";
	public static final String KEY_NEAREST_STORM_BEARING = "nearestStormBearing";
	public static final String KEY_NEAREST_STORM_DISTANCE = "nearestStormDistance";
	public static final String KEY_PRECIP_ACCUMULATION = "precipAccumulation";
	public static final String KEY_PRECIP_INTENSITY = "precipIntensity";
	public static final String KEY_PRECIP_PROBABILITY = "precipProbability";
	public static final String KEY_PRECIP_TYPE = "precipType";
	public static final String KEY_VISIBILITY = "visibility";
	public static final String KEY_WEATHER_ICON = "weatherIcon";

	// Constructors

	public WeatherData() { }

	public WeatherData(Hashtable<String, Object> hash) {
		super(hash);
	}

	// Setters and Getters

	/**
	 * @param currentTemperature -
	 */
	public void setCurrentTemperature(Temperature currentTemperature) {
		setValue(KEY_CURRENT_TEMPERATURE, currentTemperature);
	}

	/**
	 * @return currentTemperature
	 */
	public Temperature getCurrentTemperature() {
		return (Temperature) getObject(Temperature.class,KEY_CURRENT_TEMPERATURE);
	}

	/**
	 * @param temperatureHigh -
	 */
	public void setTemperatureHigh(Temperature temperatureHigh) {
		setValue(KEY_TEMPERATURE_HIGH, temperatureHigh);
	}

	/**
	 * @return temperatureHigh
	 */
	public Temperature getTemperatureHigh() {
		return (Temperature) getObject(Temperature.class,KEY_TEMPERATURE_HIGH);
	}

	/**
	 * @param temperatureLow -
	 */
	public void setTemperatureLow(Temperature temperatureLow) {
		setValue(KEY_TEMPERATURE_LOW, temperatureLow);
	}

	/**
	 * @return temperatureLow
	 */
	public Temperature getTemperatureLow() {
		return (Temperature) getObject(Temperature.class,KEY_TEMPERATURE_LOW);
	}

	/**
	 * @param apparentTemperature -
	 */
	public void setApparentTemperature(Temperature apparentTemperature) {
		setValue(KEY_APPARENT_TEMPERATURE, apparentTemperature);
	}

	/**
	 * @return apparentTemperature
	 */
	public Temperature getApparentTemperature() {
		return (Temperature) getObject(Temperature.class,KEY_APPARENT_TEMPERATURE);
	}

	/**
	 * @param apparentTemperatureHigh -
	 */
	public void setApparentTemperatureHigh(Temperature apparentTemperatureHigh) {
		setValue(KEY_APPARENT_TEMPERATURE_HIGH, apparentTemperatureHigh);
	}

	/**
	 * @return apparentTemperatureHigh
	 */
	public Temperature getApparentTemperatureHigh() {
		return (Temperature) getObject(Temperature.class,KEY_APPARENT_TEMPERATURE_HIGH);
	}

	/**
	 * @param apparentTemperatureLow -
	 */
	public void setApparentTemperatureLow(Temperature apparentTemperatureLow) {
		setValue(KEY_APPARENT_TEMPERATURE_LOW, apparentTemperatureLow);
	}

	/**
	 * @return apparentTemperatureLow
	 */
	public Temperature getApparentTemperatureLow() {
		return (Temperature) getObject(Temperature.class,KEY_APPARENT_TEMPERATURE_LOW);
	}

	/**
	 * @param weatherSummary -
	 */
	public void setWeatherSummary(String weatherSummary) {
		setValue(KEY_WEATHER_SUMMARY, weatherSummary);
	}

	/**
	 * @return weatherSummary
	 */
	public String getWeatherSummary() {
		return getString(KEY_WEATHER_SUMMARY);
	}

	/**
	 * @param time -
	 */
	public void setTime(DateTime time) {
		setValue(KEY_TIME, time);
	}

	/**
	 * @return time
	 */
	public DateTime getTime() {
		return (DateTime) getObject(DateTime.class,KEY_TIME);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @param humidity - percentage humidity
	 */
	public void setHumidity(Float humidity) {
		setValue(KEY_HUMIDITY, humidity);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @return humidity - percentage humidity
	 */
	public Float getHumidity() {
		return getFloat(KEY_HUMIDITY);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @param cloudCover - cloud cover
	 */
	public void setCloudCover(Float cloudCover) {
		setValue(KEY_CLOUD_COVER, cloudCover);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @return cloudCover - cloud cover
	 */
	public Float getCloudCover() {
		return getFloat(KEY_CLOUD_COVER);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @param moonPhase - percentage of the moon seen, e.g. 0 = no moon, 0.25 = quarter moon
	 */
	public void setMoonPhase(Float moonPhase) {
		setValue(KEY_MOON_PHASE, moonPhase);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @return moonPhase - percentage of the moon seen, e.g. 0 = no moon, 0.25 = quarter moon
	 */
	public Float getMoonPhase() {
		return getFloat(KEY_MOON_PHASE);
	}

	/**
	 * @param windBearing - In degrees, true north at 0 degrees
	 */
	public void setWindBearing(Integer windBearing) {
		setValue(KEY_WIND_BEARING, windBearing);
	}

	/**
	 * @return windBearing - In degrees, true north at 0 degrees
	 */
	public Integer getWindBearing() {
		return getInteger(KEY_WIND_BEARING);
	}

	/**
	 * @param windGust - km/hr
	 */
	public void setWindGust(Float windGust) {
		setValue(KEY_WIND_GUST, windGust);
	}

	/**
	 * @return windGust - km/hr
	 */
	public Float getWindGust() {
		return getFloat(KEY_WIND_GUST);
	}

	/**
	 * @param windSpeed - km/hr
	 */
	public void setWindSpeed(Float windSpeed) {
		setValue(KEY_WIND_SPEED, windSpeed);
	}

	/**
	 * @return windSpeed - km/hr
	 */
	public Float getWindSpeed() {
		return getFloat(KEY_WIND_SPEED);
	}

	/**
	 * @param nearestStormBearing - In degrees, true north at 0 degrees
	 */
	public void setNearestStormBearing(Integer nearestStormBearing) {
		setValue(KEY_NEAREST_STORM_BEARING, nearestStormBearing);
	}

	/**
	 * @return nearestStormBearing - In degrees, true north at 0 degrees
	 */
	public Integer getNearestStormBearing() {
		return getInteger(KEY_NEAREST_STORM_BEARING);
	}

	/**
	 * @param nearestStormDistance - In km
	 */
	public void setNearestStormDistance(Integer nearestStormDistance) {
		setValue(KEY_NEAREST_STORM_DISTANCE, nearestStormDistance);
	}

	/**
	 * @return nearestStormDistance - In km
	 */
	public Integer getNearestStormDistance() {
		return getInteger(KEY_NEAREST_STORM_DISTANCE);
	}

	/**
	 * @param precipAccumulation - cm
	 */
	public void setPrecipAccumulation(Float precipAccumulation) {
		setValue(KEY_PRECIP_ACCUMULATION, precipAccumulation);
	}

	/**
	 * @return precipAccumulation - cm
	 */
	public Float getPrecipAccumulation() {
		return getFloat(KEY_PRECIP_ACCUMULATION);
	}

	/**
	 * @param precipIntensity - cm of water per hour
	 */
	public void setPrecipIntensity(Float precipIntensity) {
		setValue(KEY_PRECIP_INTENSITY, precipIntensity);
	}

	/**
	 * @return precipIntensity - cm of water per hour
	 */
	public Float getPrecipIntensity() {
		return getFloat(KEY_PRECIP_INTENSITY);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @param precipProbability - percentage chance
	 */
	public void setPrecipProbability(Float precipProbability) {
		setValue(KEY_PRECIP_PROBABILITY, precipProbability);
	}

	/**
	 * minValue: 0, maxValue: 1
	 * @return precipProbability - percentage chance
	 */
	public Float getPrecipProbability() {
		return getFloat(KEY_PRECIP_PROBABILITY);
	}

	/**
	 * @param precipType - e.g. "rain", "snow", "sleet", "hail"
	 */
	public void setPrecipType(String precipType) {
		setValue(KEY_PRECIP_TYPE, precipType);
	}

	/**
	 * @return precipType - e.g. "rain", "snow", "sleet", "hail"
	 */
	public String getPrecipType() {
		return getString(KEY_PRECIP_TYPE);
	}

	/**
	 * @param weatherIcon -
	 */
	public void setWeatherIcon(Image weatherIcon) {
		setValue(KEY_WEATHER_ICON, weatherIcon);
	}

	/**
	 * @return weatherIcon
	 */
	public Image getWeatherIcon() {
		return (Image) getObject(Image.class, KEY_WEATHER_ICON);
	}

	/**
	 * @param visibility - In km
	 */
	public void setVisibility(Float visibility) {
		setValue(KEY_VISIBILITY, visibility);
	}

	/**
	 * @return visibility - In km
	 */
	public Float getVisibility() {
		return getFloat(KEY_VISIBILITY);
	}

}
