package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.LightName;

import java.util.Hashtable;

public class LightCapabilities extends RPCStruct {
	public static final String KEY_NAME = "name";
	public static final String KEY_DENSITY_AVAILABLE = "densityAvailable";
	public static final String KEY_SRGB_COLOR_SPACE_AVAILABLE = "sRGBColorSpaceAvailable";

	public LightCapabilities() {
	}

	public LightCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	public LightCapabilities(@NonNull LightName name) {
		this();
		setName(name);
	}

	/**
	 * Sets the name portion of the LightCapabilities class
	 *
	 * @param name
	 */
	public void setName(@NonNull LightName name) {
		setValue(KEY_NAME, name);
	}

	/**
	 * Gets the name portion of the LightCapabilities class
	 *
	 * @return LightName
	 */
	public LightName getName() {
		return (LightName) getObject(LightName.class, KEY_NAME);
	}

	/**
	 * Sets the densityAvailable portion of the LightCapabilities class
	 *
	 * @param densityAvailable Indicates if the light's density can be set remotely (similar to a dimmer).
	 */
	public void setDensityAvailable(Boolean densityAvailable) {
		setValue(KEY_DENSITY_AVAILABLE, densityAvailable);
	}

	/**
	 * Gets the densityAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the light's density can be set remotely (similar to a dimmer).
	 */
	public Boolean getDensityAvailable() {
		return getBoolean(KEY_DENSITY_AVAILABLE);
	}

	/**
	 * Sets the sRGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @param sRGBColorSpaceAvailable Indicates if the light's color can be set remotely by using the sRGB color space.
	 */
	public void setSRGBColorSpaceAvailable(Boolean sRGBColorSpaceAvailable) {
		setValue(KEY_SRGB_COLOR_SPACE_AVAILABLE, sRGBColorSpaceAvailable);
	}

	/**
	 * Gets the sRGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the light's color can be set remotely by using the sRGB color space.
	 */
	public Boolean getSRGBColorSpaceAvailable() {
		return getBoolean(KEY_SRGB_COLOR_SPACE_AVAILABLE);
	}
}
