package com.smartdevicelink.proxy.rpc.enums;

/**
 * Specifies HMI Zones in the vehicle.
 * 
 *  @since SmartDeviceLink 1.0
 */
public enum HmiZoneCapabilities {
	/**
	 * Indicates HMI available for front seat passengers.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	FRONT,
	/**
	 * Indicates HMI available for rear seat passengers.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	BACK;
	/**
     * Convert String to HMIZoneCapabilities
     * @param value String
     * @return HMIZoneCapabilities
     */

    public static HmiZoneCapabilities valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
