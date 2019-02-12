package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import java.util.Hashtable;

/**
 * Contains all the current data of the app service. The serviceType will link to which of the
 * service data objects are included in this object. (eg if service type equals MEDIA, the
 * mediaServiceData param should be included.
 */
public class AppServiceData extends RPCStruct {

	public static final String KEY_SERVICE_TYPE = "serviceType";
	public static final String KEY_SERVICE_ID   = "serviceID";
	public static final String KEY_MEDIA_SERVICE_DATA = "mediaServiceData";
	public static final String KEY_WEATHER_SERVICE_DATA = "weatherServiceData";

	// Constructors
	public AppServiceData() { }

	public AppServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public AppServiceData(@NonNull AppServiceType serviceType, @NonNull String serviceId) {
		this();
		setServiceType(serviceType);
		setServiceId(serviceId);
	}

	// Setters and Getters

	/**
	 * @param serviceType -
	 */
	public void setServiceType(@NonNull AppServiceType serviceType) {
		setValue(KEY_SERVICE_TYPE, serviceType);
	}

	/**
	 * @return serviceType -
	 */
	public AppServiceType getServiceType() {
		return (AppServiceType) getObject(AppServiceType.class,KEY_SERVICE_TYPE);
	}

	/**
	 * @param serviceId -
	 */
	public void setServiceId(@NonNull String serviceId) {
		setValue(KEY_SERVICE_ID, serviceId);
	}

	/**
	 * @return serviceId -
	 */
	public String getServiceId() {
		return getString(KEY_SERVICE_ID);
	}

	/**
	 * @param mediaServiceData -
	 */
	public void setMediaServiceData(MediaServiceData mediaServiceData) {
		setValue(KEY_MEDIA_SERVICE_DATA, mediaServiceData);
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
	public void setWeatherServiceData(WeatherServiceData weatherServiceData) {
		setValue(KEY_WEATHER_SERVICE_DATA, weatherServiceData);
	}

	/**
	 * @return weatherServiceData -
	 */
	public WeatherServiceData getWeatherServiceData() {
		return (WeatherServiceData) getObject(WeatherServiceData.class,KEY_WEATHER_SERVICE_DATA);
	}

}
