package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class PublishAppServiceResponse extends RPCResponse {

	public static final String KEY_APP_SERVICE_RECORD = "appServiceRecord";
	/**
	 * Constructs a new PublishAppServiceResponse object
	 */
	public PublishAppServiceResponse() {
		super(FunctionID.PUBLISH_APP_SERVICE.toString());
	}

	public PublishAppServiceResponse(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
	 * Constructs a new PublishAppServiceResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public PublishAppServiceResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

	// Custom Getters / Setters

	/**
	 * If the request was successful, this object will be the current status of the service record
	 * for the published service. This will include the Core supplied service ID.
	 * @param appServiceRecord - the App Service Record
	 */
	public void setServiceRecord(AppServiceRecord appServiceRecord){
		setValue(KEY_APP_SERVICE_RECORD, appServiceRecord);
	}

	/**
	 * If the request was successful, this object will be the current status of the service record
	 * for the published service. This will include the Core supplied service ID.
	 * @return appServiceRecord - the App Service Record
	 */
	public AppServiceRecord getServiceRecord(){
		return (AppServiceRecord) getObject(AppServiceRecord.class,KEY_APP_SERVICE_RECORD);
	}
}