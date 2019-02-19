package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 *  This manifest contains all the information necessary for the service to be
 *  published, activated, and allow consumers to interact with it
 */
public class AppServiceManifest extends RPCStruct {

	public static final String KEY_SERVICE_NAME = "serviceName";
	public static final String KEY_SERVICE_TYPE = "serviceType";
	public static final String KEY_SERVICE_ICON = "serviceIcon";
	public static final String KEY_ALLOW_APP_CONSUMERS = "allowAppConsumers";
	public static final String KEY_URI_PREFIX = "uriPrefix";
	public static final String KEY_URI_SCHEME = "uriScheme";
	public static final String KEY_RPC_SPEC_VERSION = "rpcSpecVersion";
	public static final String KEY_HANDLED_RPCS = "handledRPCs";
	public static final String KEY_MEDIA_SERVICE_MANIFEST = "mediaServiceManifest";
	public static final String KEY_WEATHER_SERVICE_MANIFEST = "weatherServiceManifest";

	// Constructors
	public AppServiceManifest() { }

	public AppServiceManifest(Hashtable<String, Object> hash) {
		super(hash);
	}

	public AppServiceManifest(@NonNull String serviceType) {
		this();
		setServiceType(serviceType);
	}

	// Setters and Getters
	/**
	 * Unique name of this service
	 * @param serviceName - the service name
	 */
	public void setServiceName(String serviceName){
		setValue(KEY_SERVICE_NAME, serviceName);
	}

	/**
	 * Unique name of this service
	 * @return ServiceName
	 */
	public String getServiceName(){
		return getString(KEY_SERVICE_NAME);
	}

	/**
	 * The type of service that is to be offered by this app
	 * @param serviceType - the serviceType
	 */
	public void setServiceType(String serviceType){
		setValue(KEY_SERVICE_TYPE, serviceType);
	}

	/**
	 * The type of service that is to be offered by this app
	 * @return the AppServiceType
	 */
	public String getServiceType(){
		return getString(KEY_SERVICE_TYPE);
	}

	/**
	 * The icon to be associated with this service Most likely the same as the appIcon.
	 * @param serviceIcon - The Service Icon Image
	 */
	public void setServiceIcon(Image serviceIcon){
		setValue(KEY_SERVICE_ICON, serviceIcon);
	}

	/**
	 * The icon to be associated with this service Most likely the same as the appIcon.
	 * @return serviceIcon Image
	 */
	public Image getServiceIcon(){
		return (Image) getObject(Image.class, KEY_SERVICE_ICON);
	}

	/**
	 * If true, app service consumers beyond the IVI system will be able to access this service. If false,
	 * only the IVI system will be able consume the service. If not provided, it is assumed to be false.
	 * @param allowAppConsumers - boolean
	 */
	public void setAllowAppConsumers(Boolean allowAppConsumers){
		setValue(KEY_ALLOW_APP_CONSUMERS, allowAppConsumers);
	}

	/**
	 * If true, app service consumers beyond the IVI system will be able to access this service. If false,
	 * only the IVI system will be able consume the service. If not provided, it is assumed to be false.
	 * @return allowAppConsumers - boolean
	 */
	public Boolean getAllowAppConsumers(){
		return getBoolean(KEY_ALLOW_APP_CONSUMERS);
	}

	/**
	 * The file name of the icon to be associated with this service. Most likely the same as the appIcon.
	 * @param uriPrefix - The URI prefix
	 */
	public void setUriPrefix(String uriPrefix){
		setValue(KEY_URI_PREFIX, uriPrefix);
	}

	/**
	 * The file name of the icon to be associated with this service. Most likely the same as the appIcon.
	 * @return uriPrefix - The URI prefix
	 */
	public String getUriPrefix(){
		return getString(KEY_URI_PREFIX);
	}

	/**
	 * This is a custom schema for this service. SDL will not do any verification on this param past
	 * that it has a correctly formatted JSON Object as its base. The uriScheme should contain all
	 * available actions to be taken through a PerformAppServiceInteraction request from an app service consumer.
	 * @param uriScheme - The uriScheme
	 */
	public void setUriScheme(String uriScheme){
		setValue(KEY_URI_SCHEME, uriScheme);
	}

	/**
	 * This is a custom schema for this service. SDL will not do any verification on this param past
	 * that it has a correctly formatted JSON Object as its base. The uriScheme should contain all
	 * available actions to be taken through a PerformAppServiceInteraction request from an app service consumer.
	 * @return uriScheme - The uriScheme
	 */
	public String getUriScheme(){
		return getString(KEY_URI_SCHEME);
	}

	/**
	 * This is the max RPC Spec version the app service understands. This is important during the RPC pass through functionality.
	 * If not included, it is assumed the max version of the module is acceptable.
	 * @param rpcSpecVersion - The rpcSpecVersion
	 */
	public void setRpcSpecVersion(SdlMsgVersion rpcSpecVersion){
		setValue(KEY_RPC_SPEC_VERSION, rpcSpecVersion);
	}

	/**
	 * This is the max RPC Spec version the app service understands. This is important during the RPC pass through functionality.
	 * If not included, it is assumed the max version of the module is acceptable.
	 * @return rpcSpecVersion - The rpcSpecVersion
	 */
	public SdlMsgVersion getRpcSpecVersion(){
		return (SdlMsgVersion) getObject(SdlMsgVersion.class,KEY_RPC_SPEC_VERSION);
	}

	/**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @param handledRPCs - The List of Handled RPCs
	 */
	public void setHandledRpcs(List<Integer> handledRPCs){
		setValue(KEY_HANDLED_RPCS, handledRPCs);
	}

	/**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @return handledRPCs - The List of Handled RPCs
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> getHandledRpcs(){
		return (List<Integer>) getObject(Integer.class,KEY_HANDLED_RPCS);
	}

	/**
	 * The MediaServiceManifest
	 * @param mediaServiceManifest - The mediaServiceManifest
	 */
	public void setMediaServiceManifest(MediaServiceManifest mediaServiceManifest){
		setValue(KEY_MEDIA_SERVICE_MANIFEST, mediaServiceManifest);
	}

	/**
	 * The MediaServiceManifest
	 * @return mediaServiceManifest - The mediaServiceManifest
	 */
	public MediaServiceManifest getMediaServiceManifest(){
		return (MediaServiceManifest) getObject(MediaServiceManifest.class,KEY_MEDIA_SERVICE_MANIFEST);
	}

	/**
	 * The WeatherServiceManifest
	 * @param weatherServiceManifest - The weatherServiceManifest
	 */
	public void setWeatherServiceManifest(WeatherServiceManifest weatherServiceManifest){
		setValue(KEY_WEATHER_SERVICE_MANIFEST, weatherServiceManifest);
	}

	/**
	 * The WeatherServiceManifest
	 * @return weatherServiceManifest - The weatherServiceManifest
	 */
	public WeatherServiceManifest getWeatherServiceManifest(){
		return (WeatherServiceManifest) getObject(WeatherServiceManifest.class,KEY_WEATHER_SERVICE_MANIFEST);
	}
}
