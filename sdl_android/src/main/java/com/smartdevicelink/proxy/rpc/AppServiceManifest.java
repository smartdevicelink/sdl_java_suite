package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AppServiceType;

import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 *  This manifest contains all the information necessary for the
 *  service to be published, activated, and consumers able to interact with it
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
	public static final String KEY_NAVIGATION_SERVICE_MANIFEST = "navigationServiceManifest";
	public static final String KEY_VOICE_ASSISTANT_SERVICE_MANIFEST = "voiceAssistantServiceManifest";

	// Constructors
	public AppServiceManifest() { }

	public AppServiceManifest(Hashtable<String, Object> hash) {
		super(hash);
	}

	public AppServiceManifest(@NonNull AppServiceType serviceType) {
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
	 * @param serviceType - the AppServiceType
	 */
	public void setServiceType(AppServiceType serviceType){
		setValue(KEY_SERVICE_TYPE, serviceType);
	}

	/**
	 * The type of service that is to be offered by this app
	 * @return the AppServiceType
	 */
	public AppServiceType getServiceType(){
		return (AppServiceType) getObject(AppServiceType.class, KEY_SERVICE_TYPE);
	}

	/**
	 * The file name of the icon to be associated with this service. Most likely the same as the appIcon.
	 * @param serviceIcon - The Service Icon Name
	 */
	public void setServiceIcon(String serviceIcon){
		setValue(KEY_SERVICE_ICON, serviceIcon);
	}

	/**
	 * The file name of the icon to be associated with this service. Most likely the same as the appIcon.
	 * @return serviceIcon fileName
	 */
	public String getServiceIcon(){
		return getString(KEY_SERVICE_ICON);
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
	 * This is a custom schema for this service. SDL will not do any verification on this param past that it has a correctly
	 * formatted JSON Object as its base. The uriScheme should contain all available actions to be taken through a
	 * PerformAppServiceInteraction request from an app service consumer.
	 * @param uriScheme - The uriScheme
	 */
	public void setUriScheme(JSONObject uriScheme){
		setValue(KEY_URI_SCHEME, uriScheme);
	}

	/**
	 * This is a custom schema for this service. SDL will not do any verification on this param past that it has a correctly
	 * formatted JSON Object as its base. The uriScheme should contain all available actions to be taken through a
	 * PerformAppServiceInteraction request from an app service consumer.
	 * @return uriScheme - The uriScheme
	 */
	public JSONObject getUriScheme(){
		return (JSONObject) getObject(JSONObject.class, KEY_URI_SCHEME);
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
	public void setHandledRpcs(List<FunctionID> handledRPCs){
		setValue(KEY_HANDLED_RPCS, handledRPCs);
	}

	/**
	 * This field contains the Function IDs for the RPCs that this service intends to handle correctly.
	 * This means the service will provide meaningful responses.
	 * @return handledRPCs - The List of Handled RPCs
	 */
	@SuppressWarnings("unchecked")
	public List<FunctionID> getHandledRpcs(){
		return (List<FunctionID>) getObject(FunctionID.class,KEY_HANDLED_RPCS);
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

	/**
	 * The NavigationServiceManifest
	 * @param navigationServiceManifest - The navigationServiceManifest
	 */
	/*public void setNavigationServiceManifest(NavigationServiceManifest navigationServiceManifest){
		setValue(KEY_NAVIGATION_SERVICE_MANIFEST, navigationServiceManifest);
	}*/

	/**
	 * The NavigationServiceManifest
	 * @return navigationServiceManifest - The navigationServiceManifest
	 */
	/*public NavigationServiceManifest getNavigationServiceManifest(){
		return (NavigationServiceManifest) getObject(NavigationServiceManifest.class,KEY_NAVIGATION_SERVICE_MANIFEST);
	}*/

	/**
	 * The VoiceAssistantServiceManifest
	 * @param voiceAssistantServiceManifest - The voiceAssistantServiceManifest
	 */
	/*public void setVoiceAssistantServiceManifest(VoiceAssistantServiceManifest voiceAssistantServiceManifest){
		setValue(KEY_VOICE_ASSISTANT_SERVICE_MANIFEST, voiceAssistantServiceManifest);
	}*/

	/**
	 * The VoiceAssistantServiceManifest
	 * @return voiceAssistantServiceManifest - The voiceAssistantServiceManifest
	 */
	/*public VoiceAssistantServiceManifest getVoiceAssistantServiceManifest(){
		return (VoiceAssistantServiceManifest) getObject(VoiceAssistantServiceManifest.class,KEY_VOICE_ASSISTANT_SERVICE_MANIFEST);
	}*/
}
