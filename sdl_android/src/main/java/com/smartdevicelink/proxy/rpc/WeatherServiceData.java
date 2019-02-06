package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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
	public void setLocation(LocationDetails location) {
		setValue(KEY_LOCATION, location);
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
	public void setCurrentForecast(LocationDetails currentForecast) {
		setValue(KEY_CURRENT_FORECAST, currentForecast);
	}

	/**
	 * @return currentForecast
	 */
	public LocationDetails getCurrentForecast() {
		return (LocationDetails) getObject(LocationDetails.class,KEY_CURRENT_FORECAST);
	}

	/**
	 * MinSize: 15, MaxSize: 60
	 * @param minuteForecast -
	 */
	public void setMinuteForecast(List<WeatherData> minuteForecast){
		setValue(KEY_MINUTE_FORECAST, minuteForecast);
	}

	/**
	 * MinSize: 15, MaxSize: 60
	 * @return minuteForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getMinuteForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_MINUTE_FORECAST);
	}

	/**
	 * MinSize: 1, MaxSize: 96
	 * @param hourlyForecast -
	 */
	public void setHourlyForecast(List<WeatherData> hourlyForecast){
		setValue(KEY_HOURLY_FORECAST, hourlyForecast);
	}

	/**
	 * MinSize: 1, MaxSize: 96
	 * @return hourlyForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getHourlyForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_HOURLY_FORECAST);
	}

	/**
	 * MinSize: 1, MaxSize: 30
	 * @param multidayForecast -
	 */
	public void setMultidayForecast(List<WeatherData> multidayForecast){
		setValue(KEY_MULTIDAY_FORECAST, multidayForecast);
	}

	/**
	 * MinSize: 1, MaxSize: 30
	 * @return multidayForecast
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherData> getMultidayForecast(){
		return (List<WeatherData>) getObject(WeatherData.class,KEY_MULTIDAY_FORECAST);
	}

	/**
	 * MinSize: 1, MaxSize: 10
	 * @param alerts -
	 */
	public void setAlerts(List<WeatherAlert> alerts){
		setValue(KEY_ALERTS, alerts);
	}

	/**
	 * MinSize: 1, MaxSize: 10
	 * @return alerts
	 */
	@SuppressWarnings("unchecked")
	public List<WeatherAlert> getAlerts(){
		return (List<WeatherAlert>) getObject(WeatherAlert.class,KEY_ALERTS);
	}

}
