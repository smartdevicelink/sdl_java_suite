package com.smartdevicelink.proxy.rpc.enums;
/**
 * Reflects the status of a vehicle maintenance mode.
 * 
 *@since SmartDeviceLink 2.0
 */

public enum MaintenanceModeStatus {
	NORMAL,
	NEAR,
	ACTIVE,
	FEATURE_NOT_PRESENT;

    public static MaintenanceModeStatus valueForString(String value) {
        return valueOf(value);
    }
}
