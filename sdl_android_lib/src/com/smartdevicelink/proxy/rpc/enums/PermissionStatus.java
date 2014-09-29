package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration that describes possible permission states of a policy table entry.
 * @since SmartDeviceLink 2.0
 */
public enum PermissionStatus {
	ALLOWED,
	DISALLOWED,
	USER_DISALLOWED,
	USER_CONSENT_PENDING;
	
    /**
     * Convert String to PermissionStatus
     * @param value String
     * @return PermissionStatus
     */    
	public static PermissionStatus valueForString(String value) {
        return valueOf(value);
    }
}
