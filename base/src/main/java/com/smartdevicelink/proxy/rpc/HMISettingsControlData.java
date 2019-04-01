package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DisplayMode;
import com.smartdevicelink.proxy.rpc.enums.DistanceUnit;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;

import java.util.Hashtable;

/**
 * Corresponds to "HMI_SETTINGS" ModuleType
 */

public class HMISettingsControlData extends RPCStruct {
	public static final String KEY_DISPLAY_MODE = "displayMode";
	public static final String KEY_TEMPERATURE_UNIT = "temperatureUnit";
	public static final String KEY_DISTANCE_UNIT = "distanceUnit";

	public HMISettingsControlData() {
	}

	public HMISettingsControlData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the displayMode portion of the HMISettingsControlData class
	 *
	 * @param displayMode
	 */
	public void setDisplayMode(DisplayMode displayMode) {
		setValue(KEY_DISPLAY_MODE, displayMode);
	}

	/**
	 * Gets the displayMode portion of the HMISettingsControlData class
	 *
	 * @return DisplayMode
	 */
	public DisplayMode getDisplayMode() {
		return (DisplayMode) getObject(DisplayMode.class, KEY_DISPLAY_MODE);
	}

	/**
	 * Sets the temperatureUnit portion of the HMISettingsControlData class
	 *
	 * @param temperatureUnit
	 */
	public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
		setValue(KEY_TEMPERATURE_UNIT, temperatureUnit);
	}

	/**
	 * Gets the temperatureUnit portion of the HMISettingsControlData class
	 *
	 * @return TemperatureUnit
	 */
	public TemperatureUnit getTemperatureUnit() {
		return (TemperatureUnit) getObject(TemperatureUnit.class, KEY_TEMPERATURE_UNIT);
	}

	/**
	 * Sets the distanceUnit portion of the HMISettingsControlData class
	 *
	 * @param distanceUnit
	 */
	public void setDistanceUnit(DistanceUnit distanceUnit) {
		setValue(KEY_DISTANCE_UNIT, distanceUnit);
	}

	/**
	 * Gets the distanceUnit portion of the HMISettingsControlData class
	 *
	 * @return DistanceUnit
	 */
	public DistanceUnit getDistanceUnit() {
		return (DistanceUnit) getObject(DistanceUnit.class, KEY_DISTANCE_UNIT);
	}
}
