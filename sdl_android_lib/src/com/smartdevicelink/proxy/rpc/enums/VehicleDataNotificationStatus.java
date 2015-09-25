package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the status of a vehicle data notification.
 * @since SmartDeviceLink 2.0
 * 
 * @see SoftButtonCapabilities
 * @see ButtonCapabilities
 * @see OnButtonPress 
 */
public enum VehicleDataNotificationStatus {
	/**
	 * VehicleDataNotificationStatus is not supported.
	 */
	NOT_SUPPORTED,
	/**
	 * VehicleDataNotificationStatus is normal.
	 */
	NORMAL,
	/**
	 * VehicleDataNotificationStatus is active.
	 */
	ACTIVE,
	/**
	 * VehicleDataNotificationStatus is not in use.
	 */
	NOT_USED;

    /**
     * Convert String to VehicleDataNotificationStatus
     * @param value String
     * @return VehicleDataNotificationStatus
     */    
    public static VehicleDataNotificationStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
