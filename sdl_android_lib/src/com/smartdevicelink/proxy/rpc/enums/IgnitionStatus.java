package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the status of ignition.
 * @since SmartDeviceLink 2.0
 */
public enum IgnitionStatus {
	/**
	 * Ignition status currently unknown
	 */
	UNKNOWN,
	/**
	 * Ignition is off
	 */
	OFF,
	/**
	 * Ignition is in mode accessory
	 */
	ACCESSORY,
	/**
	 * Ignition is in mode run
	 */
	RUN,
	/**
	 * Ignition is in mode run
	 */
	START,
	/**
	 * Signal is invalid
	 */
	INVALID;

    /**
     * Convert String to IgnitionStatus
     * @param value String
     * @return IgnitionStatus
     */   	
    public static IgnitionStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
