package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.util.DebugTool;

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
public class DeviceStatus extends RPCStruct {

	/**
	 * Constructs a newly allocated DeviceStatus object
	 */
    public DeviceStatus() {}
    
    /**
     * Constructs a newly allocated DeviceStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public DeviceStatus(Hashtable hash) {
        super(hash);
    }
    
    /**
     * set the voice recognition on or off
     * @param voiceRecOn
     */
    public void setVoiceRecOn(Boolean voiceRecOn) {
        if (voiceRecOn != null) {
        	store.put(Names.voiceRecOn, voiceRecOn);
        } else {
        	store.remove(Names.voiceRecOn);
        }
    }
    
    /**
     * get whether the voice recognition is on
     * @return whether the voice recognition is on
     */
    public Boolean getVoiceRecOn() {
        return (Boolean) store.get(Names.voiceRecOn);
    }
    
    /**
     * set the bluetooth connection established
     * @param btIconOn the bluetooth connection established
     */
    public void setBtIconOn(Boolean btIconOn) {
        if (btIconOn != null) {
        	store.put(Names.btIconOn, btIconOn);
        } else {
        	store.remove(Names.btIconOn);
        }
    }
    
    /**
     * get the bluetooth connection established
     * @return the bluetooth connection established
     */
    public Boolean getBtIconOn() {
        return (Boolean) store.get(Names.btIconOn);
    }
    
    /**
     * set a call is being active
     * @param callActive a call is being active
     */
    public void setCallActive(Boolean callActive) {
        if (callActive != null) {
        	store.put(Names.callActive, callActive);
        } else {
        	store.remove(Names.callActive);
        }
    }
    
    /**
     * get  a call is being active
     * @return  a call is being active
     */
    public Boolean getCallActive() {
        return (Boolean) store.get(Names.callActive);
    }
    
    /**
     * set the phone is in roaming mode
     * @param phoneRoaming  the phone is in roaming mode
     */
    public void setPhoneRoaming(Boolean phoneRoaming) {
        if (phoneRoaming != null) {
        	store.put(Names.phoneRoaming, phoneRoaming);
        } else {
        	store.remove(Names.phoneRoaming);
        }
    }
    
    /**
     * get  the phone is in roaming mode
     * @return  the phone is in roaming mode
     */
    public Boolean getPhoneRoaming() {
        return (Boolean) store.get(Names.phoneRoaming);
    }
    public void setTextMsgAvailable(Boolean textMsgAvailable) {
        if (textMsgAvailable != null) {
        	store.put(Names.textMsgAvailable, textMsgAvailable);
        } else {
        	store.remove(Names.textMsgAvailable);
        }
    }
    
    /**
     * get a textmessage is available
     * @return a textmessage is available
     */
    public Boolean getTextMsgAvailable() {
        return (Boolean) store.get(Names.textMsgAvailable);
    }
    
    /**
     * set battery level status
     * @param battLevelStatus battery level status
     */
    public void setBattLevelStatus(DeviceLevelStatus battLevelStatus) {
        if (battLevelStatus != null) {
        	store.put(Names.battLevelStatus, battLevelStatus);
        } else {
        	store.remove(Names.battLevelStatus);
        }
    }
    
    /**
     * get battery level status
     * @return battery level status
     */
    public DeviceLevelStatus getBattLevelStatus() {
        Object obj = store.get(Names.battLevelStatus);
        if (obj instanceof DeviceLevelStatus) {
            return (DeviceLevelStatus) obj;
        } else if (obj instanceof String) {
        	DeviceLevelStatus theCode = null;
            try {
                theCode = DeviceLevelStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.battLevelStatus, e);
            }
            return theCode;
        }
        return null;
    }
    
    /**
     * set the status of the stereo audio output channel
     * @param stereoAudioOutputMuted the status of the stereo audio output channel
     */
    public void setStereoAudioOutputMuted(Boolean stereoAudioOutputMuted) {
        if (stereoAudioOutputMuted != null) {
        	store.put(Names.stereoAudioOutputMuted, stereoAudioOutputMuted);
        } else {
        	store.remove(Names.stereoAudioOutputMuted);
        }
    }
    
    /**
     * get the status of the stereo audio output channel
     * @return the status of the stereo audio output channel
     */
    public Boolean getStereoAudioOutputMuted() {
        return (Boolean) store.get(Names.stereoAudioOutputMuted);
    }
    
    /**
     * set the status of the mono audio output channel
     * @param monoAudioOutputMuted the status of the mono audio output channel
     */
    public void setMonoAudioOutputMuted(Boolean monoAudioOutputMuted) {
        if (monoAudioOutputMuted != null) {
        	store.put(Names.monoAudioOutputMuted, monoAudioOutputMuted);
        } else {
        	store.remove(Names.monoAudioOutputMuted);
        }
    }
    
    /**
     * get the status of the mono audio output channel
     * @return the status of the mono audio output channel
     */
    public Boolean getMonoAudioOutputMuted() {
        return (Boolean) store.get(Names.monoAudioOutputMuted);
    }
    
    /**
     * set signal level status
     * @param signalLevelStatus signal level status
     */
    public void setSignalLevelStatus(DeviceLevelStatus signalLevelStatus) {
        if (signalLevelStatus != null) {
        	store.put(Names.signalLevelStatus, signalLevelStatus);
        } else {
        	store.remove(Names.signalLevelStatus);
        }
    }
    
    /**
     * get signal level status
     * @return signal level status
     */
    public DeviceLevelStatus getSignalLevelStatus() {
        Object obj = store.get(Names.signalLevelStatus);
        if (obj instanceof DeviceLevelStatus) {
            return (DeviceLevelStatus) obj;
        } else if (obj instanceof String) {
        	DeviceLevelStatus theCode = null;
            try {
                theCode = DeviceLevelStatus.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.signalLevelStatus, e);
            }
            return theCode;
        }
        return null;
    }
    
    /**
     * set the current primary audio source of SDL (if selected).
     * @param primaryAudioSource the current primary audio source of SDL (if selected).
     */
    public void setPrimaryAudioSource(PrimaryAudioSource primaryAudioSource) {
        if (primaryAudioSource != null) {
        	store.put(Names.primaryAudioSource, primaryAudioSource);
        } else {
        	store.remove(Names.primaryAudioSource);
        }
    }
    
    /**
     * get  the current primary audio source of SDL (if selected).
     * @return  the current primary audio source of SDL (if selected).
     */
    public PrimaryAudioSource getPrimaryAudioSource() {
        Object obj = store.get(Names.primaryAudioSource);
        if (obj instanceof PrimaryAudioSource) {
            return (PrimaryAudioSource) obj;
        } else if (obj instanceof String) {
        	PrimaryAudioSource theCode = null;
            try {
                theCode = PrimaryAudioSource.valueForString((String) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.primaryAudioSource, e);
            }
            return theCode;
        }
        return null;
    }
    public void setECallEventActive(Boolean eCallEventActive) {
        if (eCallEventActive != null) {
        	store.put(Names.eCallEventActive, eCallEventActive);
        } else {
        	store.remove(Names.eCallEventActive);
        }
    }
    public Boolean getECallEventActive() {
        return (Boolean) store.get(Names.eCallEventActive);
    }
}