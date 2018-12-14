package com.smartdevicelink.proxy.rpc.enums;

/**
 * Describes whether or not streaming audio is currently audible to the user.
 * Though provided in every OnHMIStatus notification, this information is only
 * relevant for applications that declare themselves as media apps in
 * RegisterAppInterface
 * 
 * @since SmartDeviceLink 1.0
 */
public enum AudioStreamingState {
	/**
	 * Currently streaming audio, if any, is audible to user.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	AUDIBLE,

	/**
	 * Some kind of audio mixing is taking place. Currently streaming audio, if
	 * any, is audible to the user at a lowered volume.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	ATTENUATED,
	/**
	 * Currently streaming audio, if any, is not audible to user. made via VR
	 * session.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	NOT_AUDIBLE;
	/**
     * Convert String to AudioStreamingState
     * @param value String
     * @return AudioStreamingState
     */ 

    public static AudioStreamingState valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
