package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the status of a binary vehicle data item.
 * @since SmartDeviceLink 2.0
 *
 */
public enum VehicleDataStatus {
	/**
	 * No data available
	 */
	NO_DATA_EXISTS,
	/**
	 * The vehicle item is in Off state
	 */

    OFF,
    /**
     * The vehicle item is in On state
     */

    ON;

    /**
     * Convert String to VehicleDataStatus
     * @param value String
     * @return VehicleDataStatus
     */		
    public static VehicleDataStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
