package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class HMISettingsControlCapabilities extends RPCStruct {
	public static final String KEY_MODULE_NAME = "moduleName";
	public static final String KEY_DISTANCE_UNIT_AVAILABLE = "distanceUnitAvailable";
	public static final String KEY_TEMPERATURE_UNIT_AVAILABLE = "temperatureUnitAvailable";
	public static final String KEY_DISPLAY_MODE_UNIT_AVAILABLE = "displayModeUnitAvailable";

	/**
	 * Constructs a new HMISettingsControlCapabilities object
	 */
	public HMISettingsControlCapabilities() {
	}

	/**
	 * <p>Constructs a new HMISettingsControlCapabilities object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public HMISettingsControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	public HMISettingsControlCapabilities(@NonNull String moduleName) {
		this();
		setModuleName(moduleName);
	}

	/**
	 * Sets the moduleName portion of the HMISettingsControlCapabilities class
	 *
	 * @param moduleName The short friendly name of the hmi setting module. It should not be used to identify a module by mobile application.
	 */
	public void setModuleName(@NonNull String moduleName) {
		setValue(KEY_MODULE_NAME, moduleName);
	}

	/**
	 * Gets the moduleName portion of the HMISettingsControlCapabilities class
	 *
	 * @return String - The short friendly name of the hmi setting module. It should not be used to identify a module by mobile application.
	 */
	public String getModuleName() {
		return getString(KEY_MODULE_NAME);
	}

	/**
	 * Sets the distanceUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @param distanceUnitAvailable Availability of the control of distance unit.
	 */
	public void setDistanceUnitAvailable(Boolean distanceUnitAvailable) {
		setValue(KEY_DISTANCE_UNIT_AVAILABLE, distanceUnitAvailable);
	}

	/**
	 * Gets the distanceUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of distance unit.
	 */
	public Boolean getDistanceUnitAvailable() {
		return getBoolean(KEY_DISTANCE_UNIT_AVAILABLE);
	}

	/**
	 * Sets the temperatureUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @param temperatureUnitAvailable Availability of the control of temperature unit.
	 */
	public void setTemperatureUnitAvailable(Boolean temperatureUnitAvailable) {
		setValue(KEY_TEMPERATURE_UNIT_AVAILABLE, temperatureUnitAvailable);
	}

	/**
	 * Gets the temperatureUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of temperature unit.
	 */
	public Boolean getTemperatureUnitAvailable() {
		return getBoolean(KEY_TEMPERATURE_UNIT_AVAILABLE);
	}

	/**
	 * Sets the displayModeUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @param displayModeUnitAvailable Availability of the control of HMI display mode.
	 */
	public void setDisplayModeUnitAvailable(Boolean displayModeUnitAvailable) {
		setValue(KEY_DISPLAY_MODE_UNIT_AVAILABLE, displayModeUnitAvailable);
	}

	/**
	 * Gets the displayModeUnitAvailable portion of the HMISettingsControlCapabilities class
	 *
	 * @return Boolean - Availability of the control of HMI display mode.
	 */
	public Boolean getDisplayModeUnitAvailable() {
		return getBoolean(KEY_DISPLAY_MODE_UNIT_AVAILABLE);
	}
}
