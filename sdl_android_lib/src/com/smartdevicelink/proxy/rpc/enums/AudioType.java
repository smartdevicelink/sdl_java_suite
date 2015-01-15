package com.smartdevicelink.proxy.rpc.enums;

/**
 * Describes different audio type options for PerformAudioPassThru
 * 
 * @see SoftButtonCapabilities
 * @see ButtonCapabilities
 * @see OnButtonPress
 * @since SmartDeviceLink 2.0

 */
public enum AudioType {
	/**
	 * PCM raw audio
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    PCM;

    public static AudioType valueForString(String value) {
        return valueOf(value);
    }
}
