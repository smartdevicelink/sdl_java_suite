package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

public class LightControlCapabilities extends RPCStruct {
	public static final String KEY_MODULE_NAME = "moduleName";
	public static final String KEY_SUPPORTED_LIGHTS = "supportedLights";

	/**
	 * Constructs a new LightControlCapabilities object
	 */
	public LightControlCapabilities() {
	}

	/**
	 * <p>Constructs a new LightControlCapabilities object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public LightControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated LightControlCapabilities object
	 *
	 * @param moduleName      short friendly name of the light control module.
	 * @param supportedLights An array of available LightCapabilities that are controllable.
	 */
	public LightControlCapabilities(@NonNull String moduleName, @NonNull List<LightCapabilities> supportedLights) {
		this();
		setModuleName(moduleName);
		setSupportedLights(supportedLights);
	}

	/**
	 * Sets the moduleName portion of the LightControlCapabilities class
	 *
	 * @param moduleName The short friendly name of the light control module. It should not be used to identify a module by mobile application.
	 */
	public void setModuleName(@NonNull String moduleName) {
		setValue(KEY_MODULE_NAME, moduleName);
	}

	/**
	 * Gets the moduleName portion of the LightControlCapabilities class
	 *
	 * @return String - The short friendly name of the light control module. It should not be used to identify a module by mobile application.
	 */
	public String getModuleName() {
		return getString(KEY_MODULE_NAME);
	}

	/**
	 * Gets the supportedLights portion of the LightControlCapabilities class
	 *
	 * @return List<LightCapabilities> - An array of available LightCapabilities that are controllable.
	 */
	@SuppressWarnings("unchecked")
	public List<LightCapabilities> getSupportedLights() {
		return (List<LightCapabilities>) getObject(LightCapabilities.class, KEY_SUPPORTED_LIGHTS);
	}

	/**
	 * Sets the supportedLights portion of the LightControlCapabilities class
	 *
	 * @param supportedLights An array of available LightCapabilities that are controllable.
	 */
	public void setSupportedLights(@NonNull List<LightCapabilities> supportedLights) {
		setValue(KEY_SUPPORTED_LIGHTS, supportedLights);
	}
}
