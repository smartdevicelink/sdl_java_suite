package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

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

	public GetAppServiceDataResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull AppServiceData serviceData){
		this();
		setServiceData(serviceData);
		setSuccess(success);
		setResultCode(resultCode);
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
