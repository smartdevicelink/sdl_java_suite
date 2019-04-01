package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;

/**
 * This notification includes the data that is updated from the specific service
 */
public class OnAppServiceData extends RPCNotification {

	public static final String KEY_SERVICE_DATA = "serviceData";

	// Constructors

	public OnAppServiceData() {
		super(FunctionID.ON_APP_SERVICE_DATA.toString());
	}

	public OnAppServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public OnAppServiceData(@NonNull AppServiceData serviceData) {
		this();
		setServiceData(serviceData);
	}

	// Setters and Getters

	/**
	 * @param serviceData -
	 */
	public void setServiceData(@NonNull AppServiceData serviceData){
		setParameters(KEY_SERVICE_DATA, serviceData);
	}

	/**
	 * @return serviceData
	 */
	public AppServiceData getServiceData(){
		return (AppServiceData) getObject(AppServiceData.class, KEY_SERVICE_DATA);
	}

}
