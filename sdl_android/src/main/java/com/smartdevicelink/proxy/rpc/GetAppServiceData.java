package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * This request asks the module for current data related to the specific service.
 * It also includes an option to subscribe to that service for future updates
 */
public class GetAppServiceData extends RPCRequest {

	public static final String KEY_SERVICE_TYPE = "serviceType";
	public static final String KEY_SUBSCRIBE = "subscribe";

	// Constructors
	/**
	 * Constructs a new GetAppServiceData object
	 */
	public GetAppServiceData() {
		super(FunctionID.GET_APP_SERVICE_DATA.toString());
	}

	/**
	 * Constructs a new GetAppServiceData object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public GetAppServiceData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a new GetAppServiceData object with the mandatory appServiceType parameter
	 * @param appServiceType - The appServiceType
	 */
	public GetAppServiceData(@NonNull String appServiceType) {
		this();
		setServiceType(appServiceType);
	}

	// Getters / Setters

	/**
	 * @param appServiceType - the appServiceType
	 */
	public void setServiceType(@NonNull String appServiceType){
		setParameters(KEY_SERVICE_TYPE, appServiceType);
	}

	/**
	 * @return appServiceType
	 */
	public String getServiceType(){
		return getString(KEY_SERVICE_TYPE);
	}

	/**
	 * If true, the consumer is requesting to subscribe to all future updates from the service
	 * publisher. If false, the consumer doesn't wish to subscribe and should be unsubscribed
	 * if it was previously subscribed.
	 * @param subscribe -
	 */
	public void setSubscribe(Boolean subscribe){
		setParameters(KEY_SUBSCRIBE, subscribe);
	}

	/**
	 * If true, the consumer is requesting to subscribe to all future updates from the service
	 * publisher. If false, the consumer doesn't wish to subscribe and should be unsubscribed
	 * if it was previously subscribed.
	 * @return subscribe
	 */
	public Boolean getSubscribe(){
		return getBoolean(KEY_SUBSCRIBE);
	}


}
