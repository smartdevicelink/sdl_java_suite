package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.util.JsonUtils;

/**
 * Describes the status related to a connected mobile device or SDL and if or how  it is represented in the vehicle.
 * <p><b>Parameter List
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
 */
public class DeviceStatus extends RPCObject {
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

    private Boolean voiceRecOn, btIconOn, callActive, phoneRoaming, textMsgAvailable, stereoAudioMuted,
                    monoAudioMuted, eCallEventActive;
    private String battLevelStatus, signalLevelStatus; // represents DeviceLevelStatus enum
    private String primaryAudioSource; // represents PrimaryAudioSource enum
    
	/**
	 * Constructs a newly allocated DeviceStatus object
	 */
    public DeviceStatus() {}
    
    /**
     * Creates an DeviceStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeviceStatus(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.voiceRecOn = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_VOICE_REC_ON);
            this.btIconOn = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_BT_ICON_ON);
            this.callActive = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_CALL_ACTIVE);
            this.phoneRoaming = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_PHONE_ROAMING);
            this.textMsgAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_TEXT_MSG_AVAILABLE);
            this.stereoAudioMuted = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_STEREO_AUDIO_OUTPUT_MUTED);
            this.monoAudioMuted = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_MONO_AUDIO_OUTPUT_MUTED);
            this.eCallEventActive = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_E_CALL_EVENT_ACTIVE);

            this.battLevelStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_BATT_LEVEL_STATUS);
            this.signalLevelStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SIGNAL_LEVEL_STATUS);
            this.primaryAudioSource = JsonUtils.readStringFromJsonObject(jsonObject, KEY_PRIMARY_AUDIO_SOURCE);
            break;
        }
    }
    
    /**
     * set the voice recognition on or off
     * @param voiceRecOn
     */
    public void setVoiceRecOn(Boolean voiceRecOn) {
        this.voiceRecOn = voiceRecOn;
    }
    
    /**
     * get whether the voice recognition is on
     * @return whether the voice recognition is on
     */
    public Boolean getVoiceRecOn() {
        return this.voiceRecOn;
    }
    
    /**
     * set the bluetooth connection established
     * @param btIconOn the bluetooth connection established
     */
    public void setBtIconOn(Boolean btIconOn) {
        this.btIconOn = btIconOn;
    }
    
    /**
     * get the bluetooth connection established
     * @return the bluetooth connection established
     */
    public Boolean getBtIconOn() {
        return this.btIconOn;
    }
    
    /**
     * set a call is being active
     * @param callActive a call is being active
     */
    public void setCallActive(Boolean callActive) {
        this.callActive = callActive;
    }
    
    /**
     * get  a call is being active
     * @return  a call is being active
     */
    public Boolean getCallActive() {
        return this.callActive;
    }
    
    /**
     * set the phone is in roaming mode
     * @param phoneRoaming  the phone is in roaming mode
     */
    public void setPhoneRoaming(Boolean phoneRoaming) {
        this.phoneRoaming = phoneRoaming;
    }
    
    /**
     * get  the phone is in roaming mode
     * @return  the phone is in roaming mode
     */
    public Boolean getPhoneRoaming() {
        return this.phoneRoaming;
    }
    
    public void setTextMsgAvailable(Boolean textMsgAvailable) {
        this.textMsgAvailable = textMsgAvailable;
    }
    
    /**
     * get a textmessage is available
     * @return a textmessage is available
     */
    public Boolean getTextMsgAvailable() {
        return this.textMsgAvailable;
    }
    
    /**
     * set battery level status
     * @param battLevelStatus battery level status
     */
    public void setBattLevelStatus(DeviceLevelStatus battLevelStatus) {
        this.battLevelStatus = (battLevelStatus == null) ? null : battLevelStatus.getJsonName(sdlVersion);
    }
    
    /**
     * get battery level status
     * @return battery level status
     */
    public DeviceLevelStatus getBattLevelStatus() {
        return DeviceLevelStatus.valueForJsonName(this.battLevelStatus, sdlVersion);
    }
    
    /**
     * set the status of the stereo audio output channel
     * @param stereoAudioOutputMuted the status of the stereo audio output channel
     */
    public void setStereoAudioOutputMuted(Boolean stereoAudioOutputMuted) {
        this.stereoAudioMuted = stereoAudioOutputMuted;
    }
    
    /**
     * get the status of the stereo audio output channel
     * @return the status of the stereo audio output channel
     */
    public Boolean getStereoAudioOutputMuted() {
        return this.stereoAudioMuted;
    }
    
    /**
     * set the status of the mono audio output channel
     * @param monoAudioOutputMuted the status of the mono audio output channel
     */
    public void setMonoAudioOutputMuted(Boolean monoAudioOutputMuted) {
        this.monoAudioMuted = monoAudioOutputMuted;
    }
    
    /**
     * get the status of the mono audio output channel
     * @return the status of the mono audio output channel
     */
    public Boolean getMonoAudioOutputMuted() {
        return this.monoAudioMuted;
    }
    
    /**
     * set signal level status
     * @param signalLevelStatus signal level status
     */
    public void setSignalLevelStatus(DeviceLevelStatus signalLevelStatus) {
        this.signalLevelStatus = (signalLevelStatus == null) ? null : signalLevelStatus.getJsonName(sdlVersion);
    }
    
    /**
     * get signal level status
     * @return signal level status
     */
    public DeviceLevelStatus getSignalLevelStatus() {
        return DeviceLevelStatus.valueForJsonName(this.signalLevelStatus, sdlVersion);
    }
    
    /**
     * set the current primary audio source of SDL (if selected).
     * @param primaryAudioSource the current primary audio source of SDL (if selected).
     */
    public void setPrimaryAudioSource(PrimaryAudioSource primaryAudioSource) {
        this.primaryAudioSource = (primaryAudioSource == null) ? null : primaryAudioSource.getJsonName(sdlVersion);
    }
    
    /**
     * get  the current primary audio source of SDL (if selected).
     * @return  the current primary audio source of SDL (if selected).
     */
    public PrimaryAudioSource getPrimaryAudioSource() {
        return PrimaryAudioSource.valueForJsonName(this.primaryAudioSource, sdlVersion);
    }
    
    public void setECallEventActive(Boolean eCallEventActive) {
        this.eCallEventActive = eCallEventActive;
    }
    
    public Boolean getECallEventActive() {
        return this.eCallEventActive;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_VOICE_REC_ON, this.voiceRecOn);
            JsonUtils.addToJsonObject(result, KEY_BT_ICON_ON, this.btIconOn);
            JsonUtils.addToJsonObject(result, KEY_CALL_ACTIVE, this.callActive);
            JsonUtils.addToJsonObject(result, KEY_PHONE_ROAMING, this.phoneRoaming);
            JsonUtils.addToJsonObject(result, KEY_TEXT_MSG_AVAILABLE, this.textMsgAvailable);
            JsonUtils.addToJsonObject(result, KEY_BATT_LEVEL_STATUS, this.battLevelStatus);
            JsonUtils.addToJsonObject(result, KEY_STEREO_AUDIO_OUTPUT_MUTED, this.stereoAudioMuted);
            JsonUtils.addToJsonObject(result, KEY_MONO_AUDIO_OUTPUT_MUTED, this.monoAudioMuted);
            JsonUtils.addToJsonObject(result, KEY_SIGNAL_LEVEL_STATUS, this.signalLevelStatus);
            JsonUtils.addToJsonObject(result, KEY_PRIMARY_AUDIO_SOURCE, this.primaryAudioSource);
            JsonUtils.addToJsonObject(result, KEY_E_CALL_EVENT_ACTIVE, this.eCallEventActive);
            break;
        }
        
        return result;
    }
}
