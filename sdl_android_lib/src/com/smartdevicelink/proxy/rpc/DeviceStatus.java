package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;

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
     * set the voice recognition on or off
     * @param voiceRecOn
     */
    public void setVoiceRecOn(Boolean voiceRecOn) {
        if (voiceRecOn != null) {
        	store.put(KEY_VOICE_REC_ON, voiceRecOn);
        } else {
        	store.remove(KEY_VOICE_REC_ON);
        }
    }
    
    /**
     * get whether the voice recognition is on
     * @return whether the voice recognition is on
     */
    public Boolean getVoiceRecOn() {
        return (Boolean) store.get(KEY_VOICE_REC_ON);
    }
    
    /**
     * set the bluetooth connection established
     * @param btIconOn the bluetooth connection established
     */
    public void setBtIconOn(Boolean btIconOn) {
        if (btIconOn != null) {
        	store.put(KEY_BT_ICON_ON, btIconOn);
        } else {
        	store.remove(KEY_BT_ICON_ON);
        }
    }
    
    /**
     * get the bluetooth connection established
     * @return the bluetooth connection established
     */
    public Boolean getBtIconOn() {
        return (Boolean) store.get(KEY_BT_ICON_ON);
    }
    
    /**
     * set a call is being active
     * @param callActive a call is being active
     */
    public void setCallActive(Boolean callActive) {
        if (callActive != null) {
        	store.put(KEY_CALL_ACTIVE, callActive);
        } else {
        	store.remove(KEY_CALL_ACTIVE);
        }
    }
    
    /**
     * get  a call is being active
     * @return  a call is being active
     */
    public Boolean getCallActive() {
        return (Boolean) store.get(KEY_CALL_ACTIVE);
    }
    
    /**
     * set the phone is in roaming mode
     * @param phoneRoaming  the phone is in roaming mode
     */
    public void setPhoneRoaming(Boolean phoneRoaming) {
        if (phoneRoaming != null) {
        	store.put(KEY_PHONE_ROAMING, phoneRoaming);
        } else {
        	store.remove(KEY_PHONE_ROAMING);
        }
    }
    
    /**
     * get  the phone is in roaming mode
     * @return  the phone is in roaming mode
     */
    public Boolean getPhoneRoaming() {
        return (Boolean) store.get(KEY_PHONE_ROAMING);
    }
    public void setTextMsgAvailable(Boolean textMsgAvailable) {
        if (textMsgAvailable != null) {
        	store.put(KEY_TEXT_MSG_AVAILABLE, textMsgAvailable);
        } else {
        	store.remove(KEY_TEXT_MSG_AVAILABLE);
        }
    }
    
    /**
     * get a textmessage is available
     * @return a textmessage is available
     */
    public Boolean getTextMsgAvailable() {
        return (Boolean) store.get(KEY_TEXT_MSG_AVAILABLE);
    }
    
    /**
     * set battery level status
     * @param battLevelStatus battery level status
     */
    public void setBattLevelStatus(DeviceLevelStatus battLevelStatus) {
        if (battLevelStatus != null) {
        	store.put(KEY_BATT_LEVEL_STATUS, battLevelStatus);
        } else {
        	store.remove(KEY_BATT_LEVEL_STATUS);
        }
    }
    
    /**
     * get battery level status
     * @return battery level status
     */
    public DeviceLevelStatus getBattLevelStatus() {
        Object obj = store.get(KEY_BATT_LEVEL_STATUS);
        if (obj instanceof DeviceLevelStatus) {
            return (DeviceLevelStatus) obj;
        } else if (obj instanceof String) {
        	return DeviceLevelStatus.valueForString((String) obj);
        }
        return null;
    }
    
    /**
     * set the status of the stereo audio output channel
     * @param stereoAudioOutputMuted the status of the stereo audio output channel
     */
    public void setStereoAudioOutputMuted(Boolean stereoAudioOutputMuted) {
        if (stereoAudioOutputMuted != null) {
        	store.put(KEY_STEREO_AUDIO_OUTPUT_MUTED, stereoAudioOutputMuted);
        } else {
        	store.remove(KEY_STEREO_AUDIO_OUTPUT_MUTED);
        }
    }
    
    /**
     * get the status of the stereo audio output channel
     * @return the status of the stereo audio output channel
     */
    public Boolean getStereoAudioOutputMuted() {
        return (Boolean) store.get(KEY_STEREO_AUDIO_OUTPUT_MUTED);
    }
    
    /**
     * set the status of the mono audio output channel
     * @param monoAudioOutputMuted the status of the mono audio output channel
     */
    public void setMonoAudioOutputMuted(Boolean monoAudioOutputMuted) {
        if (monoAudioOutputMuted != null) {
        	store.put(KEY_MONO_AUDIO_OUTPUT_MUTED, monoAudioOutputMuted);
        } else {
        	store.remove(KEY_MONO_AUDIO_OUTPUT_MUTED);
        }
    }
    
    /**
     * get the status of the mono audio output channel
     * @return the status of the mono audio output channel
     */
    public Boolean getMonoAudioOutputMuted() {
        return (Boolean) store.get(KEY_MONO_AUDIO_OUTPUT_MUTED);
    }
    
    /**
     * set signal level status
     * @param signalLevelStatus signal level status
     */
    public void setSignalLevelStatus(DeviceLevelStatus signalLevelStatus) {
        if (signalLevelStatus != null) {
        	store.put(KEY_SIGNAL_LEVEL_STATUS, signalLevelStatus);
        } else {
        	store.remove(KEY_SIGNAL_LEVEL_STATUS);
        }
    }
    
    /**
     * get signal level status
     * @return signal level status
     */
    public DeviceLevelStatus getSignalLevelStatus() {
        Object obj = store.get(KEY_SIGNAL_LEVEL_STATUS);
        if (obj instanceof DeviceLevelStatus) {
            return (DeviceLevelStatus) obj;
        } else if (obj instanceof String) {
        	return DeviceLevelStatus.valueForString((String) obj);
        }
        return null;
    }
    
    /**
     * set the current primary audio source of SDL (if selected).
     * @param primaryAudioSource the current primary audio source of SDL (if selected).
     */
    public void setPrimaryAudioSource(PrimaryAudioSource primaryAudioSource) {
        if (primaryAudioSource != null) {
        	store.put(KEY_PRIMARY_AUDIO_SOURCE, primaryAudioSource);
        } else {
        	store.remove(KEY_PRIMARY_AUDIO_SOURCE);
        }
    }
    
    /**
     * get  the current primary audio source of SDL (if selected).
     * @return  the current primary audio source of SDL (if selected).
     */
    public PrimaryAudioSource getPrimaryAudioSource() {
        Object obj = store.get(KEY_PRIMARY_AUDIO_SOURCE);
        if (obj instanceof PrimaryAudioSource) {
            return (PrimaryAudioSource) obj;
        } else if (obj instanceof String) {
        	return PrimaryAudioSource.valueForString((String) obj);
        }
        return null;
    }
    public void setECallEventActive(Boolean eCallEventActive) {
        if (eCallEventActive != null) {
        	store.put(KEY_E_CALL_EVENT_ACTIVE, eCallEventActive);
        } else {
        	store.remove(KEY_E_CALL_EVENT_ACTIVE);
        }
    }
    public Boolean getECallEventActive() {
        return (Boolean) store.get(KEY_E_CALL_EVENT_ACTIVE);
    }
}
