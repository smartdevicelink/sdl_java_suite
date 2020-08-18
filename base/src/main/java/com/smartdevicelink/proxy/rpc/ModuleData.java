/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

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
	public static final String KEY_MODULE_ID = "moduleId";

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

	/**
	 * Sets the Module ID of the ModuleData class
	 * @param id the id to be set
	 */
	public void setModuleId(String id) {
		setValue(KEY_MODULE_ID, id);
	}

	/**
	 * Gets the Module ID of the ModuleData class
	 * @return the Module ID
	 */
	public String getModuleId() {
		return getString(KEY_MODULE_ID);
	}
}
