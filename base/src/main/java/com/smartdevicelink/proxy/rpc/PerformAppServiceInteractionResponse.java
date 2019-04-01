package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class PerformAppServiceInteractionResponse extends RPCResponse {

	public static final String KEY_SERVICE_SPECIFIC_RESULT = "serviceSpecificResult";

	/**
	 * Constructs a new PerformAppServiceInteractionResponse object
	 */
	public PerformAppServiceInteractionResponse() {
		super(FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString());
	}

	public PerformAppServiceInteractionResponse(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
	 * Constructs a new PerformAppServiceInteractionResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public PerformAppServiceInteractionResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}

	// Setters / getters

	/**
	 * The service can provide specific result strings to the consumer through this param. These
	 * results should be described in the URI schema set in the Service Manifest
	 * @param serviceSpecificResult -
	 */
	public void setServiceSpecificResult(String serviceSpecificResult){
		setParameters(KEY_SERVICE_SPECIFIC_RESULT, serviceSpecificResult);
	}

	/**
	 * The service can provide specific result strings to the consumer through this param. These
	 * results should be described in the URI schema set in the Service Manifest
	 * @return serviceSpecificResult
	 */
	public String getServiceSpecificResult(){
		return getString(KEY_SERVICE_SPECIFIC_RESULT);
	}

}
