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
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;

import java.util.Hashtable;

/**
 * Describes the status related to a connected mobile device or SDL and if or how  it is represented in the vehicle.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>voiceRecOn</td>
 * 			<td>Boolean</td>
 * 			<td>Voice recognition is on
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>btIconOn</td>
 * 			<td>Boolean</td>
 * 			<td>Bluetooth connection established
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>callActive</td>
 * 			<td>Boolean</td>
 * 			<td>A call is being active
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>phoneRoaming</td>
 * 			<td>Boolean</td>
 * 			<td>The phone is in roaming mode
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>textMsgAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>A textmessage is available
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>battLevelStatus</td>
 * 			<td>DeviceLevelStatus</td>
 * 			<td>Battery level status
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>stereoAudioOutputMuted</td>
 * 			<td>Boolean</td>
 * 			<td>Status of the stereo audio output channel
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>monoAudioOutputMuted</td>
 * 			<td>Boolean</td>
 * 			<td>Status of the mono audio output channel
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>signalLevelStatus</td>
 * 			<td>DeviceLevelStatus</td>
 * 			<td>Signal level status
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>primaryAudioSource</td>
 * 			<td>PrimaryAudioSource</td>
 * 			<td>Reflects the current primary audio source of SDL (if selected).
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallEventActive</td>
 * 			<td>Boolean</td>
 * 			<td>Reflects, if an eCall event is active
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 * 
 * @see DeviceLevelStatus
 * @see GetVehicleData
 * @see OnVehicleData
 * 
 */
public class DeviceStatus extends RPCStruct {
    public static final String KEY_VOICE_REC_ON = "voiceRecOn";
    public static final String KEY_BT_ICON_ON = "btIconOn";
    public static final String KEY_CALL_ACTIVE = "callActive";
    public static final String KEY_PHONE_ROAMING = "phoneRoaming";
    public static final String KEY_TEXT_MSG_AVAILABLE = "textMsgAvailable";
    public static final String KEY_BATT_LEVEL_STATUS = "battLevelStatus";
    public static final String KEY_STEREO_AUDIO_OUTPUT_MUTED = "stereoAudioOutputMuted";
    public static final String KEY_MONO_AUDIO_OUTPUT_MUTED = "monoAudioOutputMuted";
    public static final String KEY_SIGNAL_LEVEL_STATUS = "signalLevelStatus";
    public static final String KEY_PRIMARY_AUDIO_SOURCE = "primaryAudioSource";
    public static final String KEY_E_CALL_EVENT_ACTIVE = "eCallEventActive";

	/**
	 * Constructs a newly allocated DeviceStatus object
	 */
    public DeviceStatus() {}
    
    /**
     * Constructs a newly allocated DeviceStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public DeviceStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated DeviceStatus object
     * @param voiceRecOn
     * @param btIconOn the bluetooth connection established
     * @param callActive a call is being active
     * @param phoneRoaming  the phone is in roaming mode
     * @param textMsgAvailable a textmessage is available
     * @param battLevelStatus battery level status
     * @param stereoAudioOutputMuted the status of the stereo audio output channel
     * @param monoAudioOutputMuted the status of the mono audio output channel
     * @param signalLevelStatus signal level status
     * @param primaryAudioSource the current primary audio source of SDL (if selected
     * @param eCallEventActive
     */
    public DeviceStatus(@NonNull Boolean voiceRecOn, @NonNull Boolean btIconOn, @NonNull Boolean callActive, @NonNull Boolean phoneRoaming, @NonNull Boolean textMsgAvailable, @NonNull DeviceLevelStatus battLevelStatus,
                        @NonNull Boolean stereoAudioOutputMuted, @NonNull Boolean monoAudioOutputMuted, @NonNull DeviceLevelStatus signalLevelStatus, @NonNull PrimaryAudioSource primaryAudioSource, @NonNull Boolean eCallEventActive) {
        this();
        setVoiceRecOn(voiceRecOn);
        setBtIconOn(btIconOn);
        setCallActive(callActive);
        setPhoneRoaming(phoneRoaming);
        setTextMsgAvailable(textMsgAvailable);
        setBattLevelStatus(battLevelStatus);
        setStereoAudioOutputMuted(stereoAudioOutputMuted);
        setMonoAudioOutputMuted(monoAudioOutputMuted);
        setSignalLevelStatus(signalLevelStatus);
        setPrimaryAudioSource(primaryAudioSource);
        setECallEventActive(eCallEventActive);
    }

    /**
     * set the voice recognition on or off
     * @param voiceRecOn
     */
    public DeviceStatus setVoiceRecOn(@NonNull Boolean voiceRecOn) {
        setValue(KEY_VOICE_REC_ON, voiceRecOn);
        return this;
    }
    
    /**
     * get whether the voice recognition is on
     * @return whether the voice recognition is on
     */
    public Boolean getVoiceRecOn() {
        return getBoolean(KEY_VOICE_REC_ON);
    }
    
    /**
     * set the bluetooth connection established
     * @param btIconOn the bluetooth connection established
     */
    public DeviceStatus setBtIconOn(@NonNull Boolean btIconOn) {
        setValue(KEY_BT_ICON_ON, btIconOn);
        return this;
    }
    
    /**
     * get the bluetooth connection established
     * @return the bluetooth connection established
     */
    public Boolean getBtIconOn() {
        return getBoolean(KEY_BT_ICON_ON);
    }
    
    /**
     * set a call is being active
     * @param callActive a call is being active
     */
    public DeviceStatus setCallActive(@NonNull Boolean callActive) {
        setValue(KEY_CALL_ACTIVE, callActive);
        return this;
    }
    
    /**
     * get  a call is being active
     * @return  a call is being active
     */
    public Boolean getCallActive() {
        return getBoolean(KEY_CALL_ACTIVE);
    }
    
    /**
     * set the phone is in roaming mode
     * @param phoneRoaming  the phone is in roaming mode
     */
    public DeviceStatus setPhoneRoaming(@NonNull Boolean phoneRoaming) {
        setValue(KEY_PHONE_ROAMING, phoneRoaming);
        return this;
    }
    
    /**
     * get  the phone is in roaming mode
     * @return  the phone is in roaming mode
     */
    public Boolean getPhoneRoaming() {
        return getBoolean(KEY_PHONE_ROAMING);
    }
    public DeviceStatus setTextMsgAvailable(@NonNull Boolean textMsgAvailable) {
        setValue(KEY_TEXT_MSG_AVAILABLE, textMsgAvailable);
        return this;
    }
    
    /**
     * get a textmessage is available
     * @return a textmessage is available
     */
    public Boolean getTextMsgAvailable() {
        return getBoolean(KEY_TEXT_MSG_AVAILABLE);
    }
    
    /**
     * set battery level status
     * @param battLevelStatus battery level status
     */
    public DeviceStatus setBattLevelStatus(@NonNull DeviceLevelStatus battLevelStatus) {
        setValue(KEY_BATT_LEVEL_STATUS, battLevelStatus);
        return this;
    }
    
    /**
     * get battery level status
     * @return battery level status
     */
    public DeviceLevelStatus getBattLevelStatus() {
        return (DeviceLevelStatus) getObject(DeviceLevelStatus.class, KEY_BATT_LEVEL_STATUS);
    }
    
    /**
     * set the status of the stereo audio output channel
     * @param stereoAudioOutputMuted the status of the stereo audio output channel
     */
    public DeviceStatus setStereoAudioOutputMuted(@NonNull Boolean stereoAudioOutputMuted) {
        setValue(KEY_STEREO_AUDIO_OUTPUT_MUTED, stereoAudioOutputMuted);
        return this;
    }
    
    /**
     * get the status of the stereo audio output channel
     * @return the status of the stereo audio output channel
     */
    public Boolean getStereoAudioOutputMuted() {
        return getBoolean(KEY_STEREO_AUDIO_OUTPUT_MUTED);
    }
    
    /**
     * set the status of the mono audio output channel
     * @param monoAudioOutputMuted the status of the mono audio output channel
     */
    public DeviceStatus setMonoAudioOutputMuted(@NonNull Boolean monoAudioOutputMuted) {
        setValue(KEY_MONO_AUDIO_OUTPUT_MUTED, monoAudioOutputMuted);
        return this;
    }
    
    /**
     * get the status of the mono audio output channel
     * @return the status of the mono audio output channel
     */
    public Boolean getMonoAudioOutputMuted() {
        return getBoolean(KEY_MONO_AUDIO_OUTPUT_MUTED);
    }
    
    /**
     * set signal level status
     * @param signalLevelStatus signal level status
     */
    public DeviceStatus setSignalLevelStatus(@NonNull DeviceLevelStatus signalLevelStatus) {
        setValue(KEY_SIGNAL_LEVEL_STATUS, signalLevelStatus);
        return this;
    }
    
    /**
     * get signal level status
     * @return signal level status
     */
    public DeviceLevelStatus getSignalLevelStatus() {
        return (DeviceLevelStatus) getObject(DeviceLevelStatus.class, KEY_SIGNAL_LEVEL_STATUS);
    }
    
    /**
     * set the current primary audio source of SDL (if selected).
     * @param primaryAudioSource the current primary audio source of SDL (if selected).
     */
    public DeviceStatus setPrimaryAudioSource(@NonNull PrimaryAudioSource primaryAudioSource) {
        setValue(KEY_PRIMARY_AUDIO_SOURCE, primaryAudioSource);
        return this;
    }
    
    /**
     * get  the current primary audio source of SDL (if selected).
     * @return  the current primary audio source of SDL (if selected).
     */
    public PrimaryAudioSource getPrimaryAudioSource() {
        return (PrimaryAudioSource) getObject(PrimaryAudioSource.class, KEY_PRIMARY_AUDIO_SOURCE);
    }
    public DeviceStatus setECallEventActive(@NonNull Boolean eCallEventActive) {
        setValue(KEY_E_CALL_EVENT_ACTIVE, eCallEventActive);
        return this;
    }
    public Boolean getECallEventActive() {
        return getBoolean(KEY_E_CALL_EVENT_ACTIVE);
    }
}
