package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * Registers a service offered by this app on the module
 */
public class PublishAppService extends RPCRequest {

	public static final String KEY_APP_SERVICE_MANIFEST = "appServiceManifest";

	// Constructors
	/**
	 * Constructs a new PublishAppService object
	 */
	public PublishAppService() {
		super(FunctionID.PUBLISH_APP_SERVICE.toString());
	}

	/**
	 * Constructs a new PublishAppService object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public PublishAppService(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a new PublishAppService object
	 * @param appServiceManifest - The appServiceManifest
	 */
	public PublishAppService(@NonNull AppServiceManifest appServiceManifest) {
			this();
			setServiceManifest(appServiceManifest);
	}

	// Getters / Setters

	/**
	 * The manifest of the service that wishes to be published.
	 * @param serviceManifest - the App Service Manifest
	 */
	public void setServiceManifest(AppServiceManifest serviceManifest){
		setParameters(KEY_APP_SERVICE_MANIFEST, serviceManifest);
	}

	/**
	 * The manifest of the service that wishes to be published.
	 * @return serviceManifest - the App Service Manifest
	 */
	public AppServiceManifest getServiceManifest(){
		return (AppServiceManifest) getObject(AppServiceManifest.class,KEY_APP_SERVICE_MANIFEST);
	}
}
