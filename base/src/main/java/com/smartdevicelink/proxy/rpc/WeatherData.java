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
	public WeatherData setCurrentTemperature( Temperature currentTemperature) {
        setValue(KEY_CURRENT_TEMPERATURE, currentTemperature);
        return this;
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
	public WeatherData setTemperatureHigh( Temperature temperatureHigh) {
        setValue(KEY_TEMPERATURE_HIGH, temperatureHigh);
        return this;
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
	public WeatherData setTemperatureLow( Temperature temperatureLow) {
        setValue(KEY_TEMPERATURE_LOW, temperatureLow);
        return this;
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
	public WeatherData setApparentTemperature( Temperature apparentTemperature) {
        setValue(KEY_APPARENT_TEMPERATURE, apparentTemperature);
        return this;
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
	public WeatherData setApparentTemperatureHigh( Temperature apparentTemperatureHigh) {
        setValue(KEY_APPARENT_TEMPERATURE_HIGH, apparentTemperatureHigh);
        return this;
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
	public WeatherData setApparentTemperatureLow( Temperature apparentTemperatureLow) {
        setValue(KEY_APPARENT_TEMPERATURE_LOW, apparentTemperatureLow);
        return this;
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
	public WeatherData setWeatherSummary( String weatherSummary) {
        setValue(KEY_WEATHER_SUMMARY, weatherSummary);
        return this;
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
	public WeatherData setTime( DateTime time) {
        setValue(KEY_TIME, time);
        return this;
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
	public WeatherData setHumidity( Float humidity) {
        setValue(KEY_HUMIDITY, humidity);
        return this;
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
	public WeatherData setCloudCover( Float cloudCover) {
        setValue(KEY_CLOUD_COVER, cloudCover);
        return this;
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
	public WeatherData setMoonPhase( Float moonPhase) {
        setValue(KEY_MOON_PHASE, moonPhase);
        return this;
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
	public WeatherData setWindBearing( Integer windBearing) {
        setValue(KEY_WIND_BEARING, windBearing);
        return this;
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
	public WeatherData setWindGust( Float windGust) {
        setValue(KEY_WIND_GUST, windGust);
        return this;
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
	public WeatherData setWindSpeed( Float windSpeed) {
        setValue(KEY_WIND_SPEED, windSpeed);
        return this;
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
	public WeatherData setNearestStormBearing( Integer nearestStormBearing) {
        setValue(KEY_NEAREST_STORM_BEARING, nearestStormBearing);
        return this;
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
	public WeatherData setNearestStormDistance( Integer nearestStormDistance) {
        setValue(KEY_NEAREST_STORM_DISTANCE, nearestStormDistance);
        return this;
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
	public WeatherData setPrecipAccumulation( Float precipAccumulation) {
        setValue(KEY_PRECIP_ACCUMULATION, precipAccumulation);
        return this;
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
	public WeatherData setPrecipIntensity( Float precipIntensity) {
        setValue(KEY_PRECIP_INTENSITY, precipIntensity);
        return this;
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
	public WeatherData setPrecipProbability( Float precipProbability) {
        setValue(KEY_PRECIP_PROBABILITY, precipProbability);
        return this;
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
	public WeatherData setPrecipType( String precipType) {
        setValue(KEY_PRECIP_TYPE, precipType);
        return this;
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
	public WeatherData setWeatherIcon( Image weatherIcon) {
        setValue(KEY_WEATHER_ICON, weatherIcon);
        return this;
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
	public WeatherData setVisibility( Float visibility) {
        setValue(KEY_VISIBILITY, visibility);
        return this;
    }

	/**
	 * @return visibility - In km
	 */
	public Float getVisibility() {
		return getFloat(KEY_VISIBILITY);
	}

}
