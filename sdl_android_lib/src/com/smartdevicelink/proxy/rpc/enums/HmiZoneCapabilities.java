package com.smartdevicelink.proxy.rpc.enums;

/**
 * Specifies HMI Zones in the vehicle.
 * 
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

    public static HmiZoneCapabilities valueForString(String value) {
        return valueOf(value);
    }
}
