package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.LightName;

import java.util.Hashtable;

public class LightCapabilities extends RPCStruct {

	public static final String KEY_NAME = "name";
	public static final String KEY_DENSITY_AVAILABLE = "densityAvailable";
	public static final String KEY_RGB_COLOR_SPACE_AVAILABLE = "rgbColorSpaceAvailable";
	public static final String KEY_STATUS_AVAILABLE = "statusAvailable";

	/**
	 * Constructs a newly allocated LightCapabilities object
	 */
	public LightCapabilities() {
	}

	/**
	 * Constructs a newly allocated LightCapabilities object indicated by the Hashtable parameter
	 *
	 * @param hash The Hashtable to use
	 */
	public LightCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated LightCapabilities object
	 *
	 * @param name name of Light
	 */
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
	 * Sets the RGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @param RGBColorSpaceAvailable Indicates if the light's color can be set remotely by using the RGB color space.
	 */
	public void setRGBColorSpaceAvailable(Boolean RGBColorSpaceAvailable) {
		setValue(KEY_RGB_COLOR_SPACE_AVAILABLE, RGBColorSpaceAvailable);
	}

	/**
	 * Gets the RGBColorSpaceAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the light's color can be set remotely by using the RGB color space.
	 */
	public Boolean getRGBColorSpaceAvailable() {
		return getBoolean(KEY_RGB_COLOR_SPACE_AVAILABLE);
	}

	/**
	 * Sets the statusAvailable portion of the LightCapabilities class
	 *
	 * @param statusAvailable Indicates if the status (ON/OFF) can be set remotely. App shall not use read-only values (RAMP_UP/RAMP_DOWN/UNKNOWN/INVALID) in a setInteriorVehicleData request.
	 */
	public void setStatusAvailable(Boolean statusAvailable) {
		setValue(KEY_STATUS_AVAILABLE, statusAvailable);
	}

	/**
	 * Gets the statusAvailable portion of the LightCapabilities class
	 *
	 * @return Boolean - Indicates if the status (ON/OFF) can be set remotely. App shall not use read-only values (RAMP_UP/RAMP_DOWN/UNKNOWN/INVALID) in a setInteriorVehicleData request.
	 */
	public Boolean getStatusAvailable() {
		return getBoolean(KEY_STATUS_AVAILABLE);
	}
}
