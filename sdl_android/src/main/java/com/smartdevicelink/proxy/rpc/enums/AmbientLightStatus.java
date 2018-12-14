package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the ambient light sensor.
 * 
 * @since SmartDeviceLink 2.3.2
 * 
 * @see SoftButtonCapabilities
 * @see ButtonCapabilities
 * @see OnButtonPress
 *
 */

public enum AmbientLightStatus {
	NIGHT,
	TWILIGHT_1,
	TWILIGHT_2,
	TWILIGHT_3,
	TWILIGHT_4,
	DAY,
	UNKNOWN,
	INVALID;
	/**
     * Convert String to AmbientLightStatus
     * @param value String
     * @return AmbientLightStatus
     */ 

    public static AmbientLightStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
