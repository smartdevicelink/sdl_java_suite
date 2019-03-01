package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class PerformAppServiceInteraction extends RPCRequest {

	public static final String KEY_SERVICE_URI = "serviceUri";
	public static final String KEY_SERVICE_ID = "serviceID";
	public static final String KEY_ORIGIN_APP = "originApp";
	public static final String KEY_REQUEST_SERVICE_ACTIVE = "requestServiceActive";

	// Constructors

	public PerformAppServiceInteraction() {
		super(FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString());
	}

	public PerformAppServiceInteraction(Hashtable<String, Object> hash) {
		super(hash);
	}

	public PerformAppServiceInteraction(@NonNull String serviceUri, @NonNull String appServiceId, @NonNull String originApp) {
		this();
		setServiceUri(serviceUri);
		setAppServiceId(appServiceId);
		setOriginApp(originApp);
	}

	/**
	 * Fully qualified URI based on the URI prefix and URI scheme the app service provided. SDL
	 * makes no guarantee that this URI is correct.
	 * @param serviceUri -
	 */
	public void setServiceUri(@NonNull String serviceUri){
		setParameters(KEY_SERVICE_URI, serviceUri);
	}

	/**
	 * Fully qualified URI based on the URI prefix and URI scheme the app service provided. SDL
	 * makes no guarantee that this URI is correct.
	 * @return serviceUri
	 */
	public String getServiceUri(){
		return getString(KEY_SERVICE_URI);
	}

	/**
	 * The service ID that the app consumer wishes to send this URI.
	 * @param appServiceId -
	 */
	public void setAppServiceId(@NonNull String appServiceId){
		setParameters(KEY_SERVICE_ID, appServiceId);
	}

	/**
	 * The service ID that the app consumer wishes to send this URI.
	 * @return appServiceId
	 */
	public String getAppServiceId(){
		return getString(KEY_SERVICE_ID);
	}

	/**
	 * This string is the appID of the app requesting the app service provider take the specific action.
	 * @param originApp -
	 */
	public void setOriginApp(@NonNull String originApp){
		setParameters(KEY_ORIGIN_APP, originApp);
	}

	/**
	 * This string is the appID of the app requesting the app service provider take the specific action.
	 * @return originApp
	 */
	public String getOriginApp(){
		return getString(KEY_ORIGIN_APP);
	}

	/**
	 * This flag signals the requesting consumer would like this service to become the active primary
	 * service of the destination's type.
	 * @param requestServiceActive -
	 */
	public void setRequestServiceActive(Boolean requestServiceActive){
		setParameters(KEY_REQUEST_SERVICE_ACTIVE, requestServiceActive);
	}

	/**
	 * This string is the appID of the app requesting the app service provider take the specific action.
	 * @return requestServiceActive
	 */
	public Boolean getRequestServiceActive(){
		return getBoolean(KEY_REQUEST_SERVICE_ACTIVE);
	}

}
