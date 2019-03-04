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

	public WeatherServiceManifest() { }

	public WeatherServiceManifest(Hashtable<String, Object> hash) {
		super(hash);
	}

	// Setters and Getters
	/**
	 * Set whether the current forecast is supported
	 * @param currentForecastSupported -
	 */
	public void setCurrentForecastSupported(Boolean currentForecastSupported){
		setValue(KEY_CURRENT_FORECAST_SUPPORTED, currentForecastSupported);
	}

	/**
	 * Get whether the current forecast is supported
	 * @return currentForecastSupported
	 */
	public Boolean getCurrentForecastSupported(){
		return getBoolean(KEY_CURRENT_FORECAST_SUPPORTED);
	}

	/**
	 * Set the max multi day forecast amount
	 * @param maxMultidayForecastAmount -
	 */
	public void setMaxMultidayForecastAmount(Integer maxMultidayForecastAmount){
		setValue(KEY_MAX_MULTIDAY_FORECAST_AMOUNT, maxMultidayForecastAmount);
	}

	/**
	 * Get the max multi day forecast amount
	 * @return maxMultidayForecastAmount
	 */
	public Integer getMaxMultidayForecastAmount(){
		return getInteger(KEY_MAX_MULTIDAY_FORECAST_AMOUNT);
	}

	/**
	 * Set the max hourly forecast amount
	 * @param maxHourlyForecastAmount -
	 */
	public void setMaxHourlyForecastAmount(Integer maxHourlyForecastAmount){
		setValue(KEY_MAX_HOURLY_FORECAST_AMOUNT, maxHourlyForecastAmount);
	}

	/**
	 * Get the max hourly forecast amount
	 * @return maxHourlyForecastAmount
	 */
	public Integer getMaxHourlyForecastAmount(){
		return getInteger(KEY_MAX_HOURLY_FORECAST_AMOUNT);
	}

	/**
	 * Set the max minutely forecast amount
	 * @param maxMinutelyForecastAmount -
	 */
	public void setMaxMinutelyForecastAmount(Integer maxMinutelyForecastAmount){
		setValue(KEY_MAX_MINUTELY_FORECAST_AMOUNT, maxMinutelyForecastAmount);
	}

	/**
	 * Get the max minutely forecast amount
	 * @return maxMinutelyForecastAmount
	 */
	public Integer getMaxMinutelyForecastAmount(){
		return getInteger(KEY_MAX_MINUTELY_FORECAST_AMOUNT);
	}

	/**
	 * Set whether the weather is supported for the current location
	 * @param weatherForLocationSupported -
	 */
	public void setWeatherForLocationSupported(Boolean weatherForLocationSupported){
		setValue(KEY_WEATHER_FOR_LOCATION_SUPPORTED, weatherForLocationSupported);
	}

	/**
	 * Get whether the weather is supported for the current location
	 * @return weatherForLocationSupported
	 */
	public Boolean getWeatherForLocationSupported(){
		return getBoolean(KEY_WEATHER_FOR_LOCATION_SUPPORTED);
	}
}
