package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of given vehicle component.
 * 
 *@since SmartDeviceLink 2.3.2 
 *
 */

public enum VehicleDataActiveStatus {
	INACTIVE_NOT_CONFIRMED,
	INACTIVE_CONFIRMED,
	ACTIVE_NOT_CONFIRMED,
	ACTIVE_CONFIRMED,
    FAULT;
	/**
     * Convert String to VehicleDataActiveStatus
     * @param value String
     * @return VehicleDataActiveStatus
     */

    public static VehicleDataActiveStatus valueForString(String value) {
        return valueOf(value);
    }
}
