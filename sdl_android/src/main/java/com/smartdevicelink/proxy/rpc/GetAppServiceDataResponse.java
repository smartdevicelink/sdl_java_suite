package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

/**
 * This response includes the data that is requested from the specific service
 */
public class GetAppServiceDataResponse extends RPCResponse {

	public static final String KEY_SERVICE_DATA = "serviceData";

	// Constructors

	public GetAppServiceDataResponse(){
		super(FunctionID.GET_APP_SERVICE_DATA.toString());
	}

	public GetAppServiceDataResponse(Hashtable<String, Object> hash){
		super(hash);
	}

	public GetAppServiceDataResponse(@NonNull AppServiceData serviceData){
		this();
		setServiceData(serviceData);
	}

	// Setters and Getters

	/**
	 * @param serviceData -
	 */
	public void setServiceData(AppServiceData serviceData){
		setParameters(KEY_SERVICE_DATA, serviceData);
	}

	/**
	 * @return serviceData
	 */
	public AppServiceData getServiceData(){
		return (AppServiceData) getObject(AppServiceData.class, KEY_SERVICE_DATA);
	}

}
