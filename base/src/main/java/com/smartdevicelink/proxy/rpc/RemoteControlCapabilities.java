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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

public class RemoteControlCapabilities extends RPCStruct {
	public static final String KEY_CLIMATE_CONTROL_CAPABILITIES = "climateControlCapabilities";
	public static final String KEY_RADIO_CONTROL_CAPABILITIES = "radioControlCapabilities";
	public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
	public static final String KEY_SEAT_CONTROL_CAPABILITIES = "seatControlCapabilities";
	public static final String KEY_AUDIO_CONTROL_CAPABILITIES = "audioControlCapabilities";
	public static final String KEY_HMI_SETTINGS_CONTROL_CAPABILITIES = "hmiSettingsControlCapabilities";
	public static final String KEY_LIGHT_CONTROL_CAPABILITIES = "lightControlCapabilities";

	public RemoteControlCapabilities() {
	}

	public RemoteControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the climateControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param climateControlCapabilities If included, the platform supports RC climate controls.
	 *                                   For this baseline version, maxsize=1. i.e. only one climate control module is supported.
	 */
	public void setClimateControlCapabilities(List<ClimateControlCapabilities> climateControlCapabilities) {
		setValue(KEY_CLIMATE_CONTROL_CAPABILITIES, climateControlCapabilities);
	}

	/**
	 * Gets the climateControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<ClimateControlCapabilities>
	 * If included, the platform supports RC climate controls.
	 * For this baseline version, maxsize=1. i.e. only one climate control module is supported.
	 */
	public List<ClimateControlCapabilities> getClimateControlCapabilities() {
		return (List<ClimateControlCapabilities>) getObject(ClimateControlCapabilities.class, KEY_CLIMATE_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the radioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param radioControlCapabilities If included, the platform supports RC climate controls.
	 *                                 For this baseline version, maxsize=1. i.e. only one radio control module is supported.
	 */
	public void setRadioControlCapabilities(List<RadioControlCapabilities> radioControlCapabilities) {
		setValue(KEY_RADIO_CONTROL_CAPABILITIES, radioControlCapabilities);
	}

	/**
	 * Gets the radioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<RadioControlCapabilities>
	 * If included, the platform supports RC climate controls.
	 * For this baseline version, maxsize=1. i.e. only one radio control module is supported.
	 */
	public List<RadioControlCapabilities> getRadioControlCapabilities() {
		return (List<RadioControlCapabilities>) getObject(RadioControlCapabilities.class, KEY_RADIO_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the buttonCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param buttonCapabilities If included, the platform supports RC button controls with the included button names.
	 */
	public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
		setValue(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
	}

	/**
	 * Gets the buttonCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<ButtonCapabilities>
	 * If included, the platform supports RC button controls with the included button names.
	 */
	public List<ButtonCapabilities> getButtonCapabilities() {
		return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
	}

	/**
	 * Sets the seatControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param seatControlCapabilities If included, the platform supports seat controls.
	 */
	public void setSeatControlCapabilities(List<SeatControlCapabilities> seatControlCapabilities) {
		setValue(KEY_SEAT_CONTROL_CAPABILITIES, seatControlCapabilities);
	}

	/**
	 * Gets the seatControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<SeatControlCapabilities>
	 * If included, the platform supports seat controls.
	 */
	public List<SeatControlCapabilities> getSeatControlCapabilities() {
		return (List<SeatControlCapabilities>) getObject(SeatControlCapabilities.class, KEY_SEAT_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the audioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param audioControlCapabilities If included, the platform supports audio controls.
	 */
	public void setAudioControlCapabilities(List<AudioControlCapabilities> audioControlCapabilities) {
		setValue(KEY_AUDIO_CONTROL_CAPABILITIES, audioControlCapabilities);
	}

	/**
	 * Gets the audioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<AudioControlCapabilities>
	 * If included, the platform supports audio controls.
	 */
	public List<AudioControlCapabilities> getAudioControlCapabilities() {
		return (List<AudioControlCapabilities>) getObject(AudioControlCapabilities.class, KEY_AUDIO_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the hmiSettingsControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param hmiSettingsControlCapabilities If included, the platform supports hmi setting controls.
	 */
	public void setHmiSettingsControlCapabilities(HMISettingsControlCapabilities hmiSettingsControlCapabilities) {
		setValue(KEY_HMI_SETTINGS_CONTROL_CAPABILITIES, hmiSettingsControlCapabilities);
	}

	/**
	 * Gets the hmiSettingsControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return HMISettingsControlCapabilities - If included, the platform supports hmi setting controls.
	 */
	public HMISettingsControlCapabilities getHmiSettingsControlCapabilities() {
		return (HMISettingsControlCapabilities) getObject(HMISettingsControlCapabilities.class, KEY_HMI_SETTINGS_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the lightControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param lightControlCapabilities If included, the platform supports light controls.
	 */
	public void setLightControlCapabilities(LightControlCapabilities lightControlCapabilities) {
		setValue(KEY_LIGHT_CONTROL_CAPABILITIES, lightControlCapabilities);
	}

	/**
	 * Gets the lightControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return LightControlCapabilities - If included, the platform supports light controls.
	 */
	public LightControlCapabilities getLightControlCapabilities() {
		return (LightControlCapabilities) getObject(LightControlCapabilities.class, KEY_LIGHT_CONTROL_CAPABILITIES);
	}
}
