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
    /**
     * Convert String to AudioType
     * @param value String
     * @return AudioType
     */    

    public static AudioType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
