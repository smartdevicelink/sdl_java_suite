package com.smartdevicelink.proxy.rpc.enums;

/**
 * Describes different audio type options for PerformAudioPassThru
 * 
 */
public enum AudioType {
	/**
	 * PCM raw audio
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    PCM;

    public static AudioType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
