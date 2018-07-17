package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class SeatControlCapabilities extends RPCStruct {
	public static final String KEY_MODULE_NAME = "moduleName";
	public static final String KEY_HEATING_ENABLED_AVAILABLE = "heatingEnabledAvailable";
	public static final String KEY_COOLING_ENABLED_AVAILABLE = "coolingEnabledAvailable";
	public static final String KEY_HEATING_LEVEL_AVAILABLE = "heatingLevelAvailable";
	public static final String KEY_COOLING_LEVEL_AVAILABLE = "coolingLevelAvailable";
	public static final String KEY_HORIZONTAL_POSITION_AVAILABLE = "horizontalPositionAvailable";
	public static final String KEY_VERTICAL_POSITION_AVAILABLE = "verticalPositionAvailable";
	public static final String KEY_FRONT_VERTICAL_POSITION_AVAILABLE = "frontVerticalPositionAvailable";
	public static final String KEY_BACK_VERTICAL_POSITION_AVAILABLE = "backVerticalPositionAvailable";
	public static final String KEY_BACK_TILT_ANGLE_AVAILABLE = "backTiltAngleAvailable";
	public static final String KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE = "headSupportHorizontalPositionAvailable";
	public static final String KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE = "headSupportVerticalPositionAvailable";
	public static final String KEY_MASSAGE_ENABLED_AVAILABLE = "massageEnabledAvailable";
	public static final String KEY_MASSAGE_MODE_AVAILABLE = "massageModeAvailable";
	public static final String KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE = "massageCushionFirmnessAvailable";
	public static final String KEY_MEMORY_AVAILABLE = "memoryAvailable";

	/**
	 * Constructs a new SeatControlCapabilities object
	 */
	public SeatControlCapabilities() {
	}

	/**
	 * <p>Constructs a new SeatControlCapabilities object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public SeatControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated SeatControlCapabilities object
	 * @param moduleName short friendly name of the light control module.
	 */
	public SeatControlCapabilities(@NonNull String moduleName) {
		this();
		setModuleName(moduleName);
	}

	/**
	 * Get the moduleName portion of the SeatControlCapabilities class
	 *
	 * @return String
	 */
	public String getModuleName() {
		return getString(KEY_MODULE_NAME);
	}

	/**
	 * Sets the moduleName portion of the SeatControlCapabilities class
	 *
	 * @param moduleName -  The short friendly name of the light control module. It should not be used to identify a module by mobile application.
	 */
	public void setModuleName(@NonNull String moduleName) {
		setValue(KEY_MODULE_NAME, moduleName);
	}

	/**
	 * Sets the heatingEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @param heatingEnabledAvailable
	 */
	public void setHeatingEnabledAvailable(Boolean heatingEnabledAvailable) {
		setValue(KEY_HEATING_ENABLED_AVAILABLE, heatingEnabledAvailable);
	}

	/**
	 * Gets the heatingEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getHeatingEnabledAvailable() {
		return getBoolean(KEY_HEATING_ENABLED_AVAILABLE);
	}

	/**
	 * Sets the coolingEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @param coolingEnabledAvailable
	 */
	public void setCoolingEnabledAvailable(Boolean coolingEnabledAvailable) {
		setValue(KEY_COOLING_ENABLED_AVAILABLE, coolingEnabledAvailable);
	}

	/**
	 * Gets the coolingEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getCoolingEnabledAvailable() {
		return getBoolean(KEY_COOLING_ENABLED_AVAILABLE);
	}

	/**
	 * Sets the heatingLevelAvailable portion of the SeatControlCapabilities class
	 *
	 * @param heatingLevelAvailable
	 */
	public void setHeatingLevelAvailable(Boolean heatingLevelAvailable) {
		setValue(KEY_HEATING_LEVEL_AVAILABLE, heatingLevelAvailable);
	}

	/**
	 * Gets the heatingLevelAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getHeatingLevelAvailable() {
		return getBoolean(KEY_HEATING_LEVEL_AVAILABLE);
	}

	/**
	 * Sets the coolingLevelAvailable portion of the SeatControlCapabilities class
	 *
	 * @param coolingLevelAvailable
	 */
	public void setCoolingLevelAvailable(Boolean coolingLevelAvailable) {
		setValue(KEY_COOLING_LEVEL_AVAILABLE, coolingLevelAvailable);
	}

	/**
	 * Gets the coolingLevelAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getCoolingLevelAvailable() {
		return getBoolean(KEY_COOLING_LEVEL_AVAILABLE);
	}

	/**
	 * Sets the horizontalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param horizontalPositionAvailable
	 */
	public void setHorizontalPositionAvailable(Boolean horizontalPositionAvailable) {
		setValue(KEY_HORIZONTAL_POSITION_AVAILABLE, horizontalPositionAvailable);
	}

	/**
	 * Gets the horizontalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getHorizontalPositionAvailable() {
		return getBoolean(KEY_HORIZONTAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the verticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param verticalPositionAvailable
	 */
	public void setVerticalPositionAvailable(Boolean verticalPositionAvailable) {
		setValue(KEY_VERTICAL_POSITION_AVAILABLE, verticalPositionAvailable);
	}

	/**
	 * Gets the verticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getVerticalPositionAvailable() {
		return getBoolean(KEY_VERTICAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the frontVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param frontVerticalPositionAvailable
	 */
	public void setFrontVerticalPositionAvailable(Boolean frontVerticalPositionAvailable) {
		setValue(KEY_FRONT_VERTICAL_POSITION_AVAILABLE, frontVerticalPositionAvailable);
	}

	/**
	 * Gets the frontVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getFrontVerticalPositionAvailable() {
		return getBoolean(KEY_FRONT_VERTICAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the backVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param backVerticalPositionAvailable
	 */
	public void setBackVerticalPositionAvailable(Boolean backVerticalPositionAvailable) {
		setValue(KEY_BACK_VERTICAL_POSITION_AVAILABLE, backVerticalPositionAvailable);
	}

	/**
	 * Gets the backVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getBackVerticalPositionAvailable() {
		return getBoolean(KEY_BACK_VERTICAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the backTiltAngleAvailable portion of the SeatControlCapabilities class
	 *
	 * @param backTiltAngleAvailable
	 */
	public void setBackTiltAngleAvailable(Boolean backTiltAngleAvailable) {
		setValue(KEY_BACK_TILT_ANGLE_AVAILABLE, backTiltAngleAvailable);
	}

	/**
	 * Gets the backTiltAngleAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getBackTiltAngleAvailable() {
		return getBoolean(KEY_BACK_TILT_ANGLE_AVAILABLE);
	}

	/**
	 * Sets the headSupportHorizontalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param headSupportHorizontalPositionAvailable
	 */
	public void setHeadSupportHorizontalPositionAvailable(Boolean headSupportHorizontalPositionAvailable) {
		setValue(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE, headSupportHorizontalPositionAvailable);
	}

	/**
	 * Gets the headSupportHorizontalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getHeadSupportHorizontalPositionAvailable() {
		return getBoolean(KEY_HEAD_SUPPORT_HORIZONTAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the headSupportVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @param headSupportVerticalPositionAvailable
	 */
	public void setHeadSupportVerticalPositionAvailable(Boolean headSupportVerticalPositionAvailable) {
		setValue(KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE, headSupportVerticalPositionAvailable);
	}

	/**
	 * Gets the headSupportVerticalPositionAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getHeadSupportVerticalPositionAvailable() {
		return getBoolean(KEY_HEAD_SUPPORT_VERTICAL_POSITION_AVAILABLE);
	}

	/**
	 * Sets the massageEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @param massageEnabledAvailable
	 */
	public void setMassageEnabledAvailable(Boolean massageEnabledAvailable) {
		setValue(KEY_MASSAGE_ENABLED_AVAILABLE, massageEnabledAvailable);
	}

	/**
	 * Gets the massageEnabledAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getMassageEnabledAvailable() {
		return getBoolean(KEY_MASSAGE_ENABLED_AVAILABLE);
	}

	/**
	 * Sets the massageModeAvailable portion of the SeatControlCapabilities class
	 *
	 * @param massageModeAvailable
	 */
	public void setMassageModeAvailable(Boolean massageModeAvailable) {
		setValue(KEY_MASSAGE_MODE_AVAILABLE, massageModeAvailable);
	}

	/**
	 * Gets the massageModeAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getMassageModeAvailable() {
		return getBoolean(KEY_MASSAGE_MODE_AVAILABLE);
	}

	/**
	 * Sets the massageCushionFirmnessAvailable portion of the SeatControlCapabilities class
	 *
	 * @param massageCushionFirmnessAvailable
	 */
	public void setMassageCushionFirmnessAvailable(Boolean massageCushionFirmnessAvailable) {
		setValue(KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE, massageCushionFirmnessAvailable);
	}

	/**
	 * Gets the massageCushionFirmnessAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getMassageCushionFirmnessAvailable() {
		return getBoolean(KEY_MASSAGE_CUSHION_FIRMNESS_AVAILABLE);
	}

	/**
	 * Sets the memoryAvailable portion of the SeatControlCapabilities class
	 *
	 * @param memoryAvailable
	 */
	public void setMemoryAvailable(Boolean memoryAvailable) {
		setValue(KEY_MEMORY_AVAILABLE, memoryAvailable);
	}

	/**
	 * Gets the memoryAvailable portion of the SeatControlCapabilities class
	 *
	 * @return Boolean.
	 */
	public Boolean getMemoryAvailable() {
		return getBoolean(KEY_MEMORY_AVAILABLE);
	}
}
