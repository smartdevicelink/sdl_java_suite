package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;

import java.util.Hashtable;

public class ModuleData extends RPCStruct {
	public static final String KEY_MODULE_TYPE = "moduleType";
	public static final String KEY_RADIO_CONTROL_DATA = "radioControlData";
	public static final String KEY_CLIMATE_CONTROL_DATA = "climateControlData";
	public static final String KEY_SEAT_CONTROL_DATA = "seatControlData";
	public static final String KEY_AUDIO_CONTROL_DATA = "audioControlData";
	public static final String KEY_LIGHT_CONTROL_DATA = "lightControlData";
	public static final String KEY_HMI_SETTINGS_CONTROL_DATA = "hmiSettingsControlData";

	public ModuleData() {
	}

	public ModuleData(Hashtable<String, Object> hash) {
		super(hash);
	}

	public ModuleData(@NonNull ModuleType moduleType) {
		this();
		setModuleType(moduleType);
	}

	/**
	 * Sets the moduleType portion of the ModuleData class
	 *
	 * @param moduleType The moduleType indicates which type of data should be changed and identifies which data object exists in this struct.
	 *                   For example, if the moduleType is CLIMATE then a "climateControlData" should exist
	 */
	public void setModuleType(@NonNull ModuleType moduleType) {
		setValue(KEY_MODULE_TYPE, moduleType);
	}

	/**
	 * Gets the moduleType portion of the ModuleData class
	 *
	 * @return ModuleType - The moduleType indicates which type of data should be changed and identifies which data object exists in this struct.
	 * For example, if the moduleType is CLIMATE then a "climateControlData" should exist.
	 */
	public ModuleType getModuleType() {
		return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
	}

	/**
	 * Sets the radioControlData portion of the ModuleData class
	 *
	 * @param radioControlData
	 */
	public void setRadioControlData(RadioControlData radioControlData) {
		setValue(KEY_RADIO_CONTROL_DATA, radioControlData);
	}

	/**
	 * Gets the radioControlData portion of the ModuleData class
	 *
	 * @return RadioControlData
	 */
	public RadioControlData getRadioControlData() {
		return (RadioControlData) getObject(RadioControlData.class, KEY_RADIO_CONTROL_DATA);
	}

	/**
	 * Sets the climateControlData portion of the ModuleData class
	 *
	 * @param climateControlData
	 */
	public void setClimateControlData(ClimateControlData climateControlData) {
		setValue(KEY_CLIMATE_CONTROL_DATA, climateControlData);
	}

	/**
	 * Gets the climateControlData portion of the ModuleData class
	 *
	 * @return ClimateControlData
	 */
	public ClimateControlData getClimateControlData() {
		return (ClimateControlData) getObject(ClimateControlData.class, KEY_CLIMATE_CONTROL_DATA);
	}

	/**
	 * Sets the seatControlData portion of the ModuleData class
	 *
	 * @param seatControlData
	 */
	public void setSeatControlData(SeatControlData seatControlData) {
		setValue(KEY_SEAT_CONTROL_DATA, seatControlData);
	}

	/**
	 * Gets the seatControlData portion of the ModuleData class
	 *
	 * @return SeatControlData
	 */
	public SeatControlData getSeatControlData() {
		return (SeatControlData) getObject(SeatControlData.class, KEY_SEAT_CONTROL_DATA);
	}

	/**
	 * Sets the audioControlData portion of the ModuleData class
	 *
	 * @param audioControlData
	 */
	public void setAudioControlData(AudioControlData audioControlData) {
		setValue(KEY_AUDIO_CONTROL_DATA, audioControlData);
	}

	/**
	 * Gets the audioControlData portion of the ModuleData class
	 *
	 * @return AudioControlData
	 */
	public AudioControlData getAudioControlData() {
		return (AudioControlData) getObject(AudioControlData.class, KEY_AUDIO_CONTROL_DATA);
	}

	/**
	 * Sets the lightControlData portion of the ModuleData class
	 *
	 * @param lightControlData
	 */
	public void setLightControlData(LightControlData lightControlData) {
		setValue(KEY_LIGHT_CONTROL_DATA, lightControlData);
	}

	/**
	 * Gets the lightControlData portion of the ModuleData class
	 *
	 * @return LightControlData
	 */
	public LightControlData getLightControlData() {
		return (LightControlData) getObject(LightControlData.class, KEY_LIGHT_CONTROL_DATA);
	}

	/**
	 * Sets the hmiSettingsControlData portion of the ModuleData class
	 *
	 * @param hmiSettingsControlData
	 */
	public void setHmiSettingsControlData(HMISettingsControlData hmiSettingsControlData) {
		setValue(KEY_HMI_SETTINGS_CONTROL_DATA, hmiSettingsControlData);
	}

	/**
	 * Gets the hmiSettingsControlData portion of the ModuleData class
	 *
	 * @return HMISettingsControlData
	 */
	public HMISettingsControlData getHmiSettingsControlData() {
		return (HMISettingsControlData) getObject(HMISettingsControlData.class, KEY_HMI_SETTINGS_CONTROL_DATA);
	}
}
