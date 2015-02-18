package com.smartdevicelink.proxy.rpc.enums;

/**
 * The volume status of a vehicle component
 * @since SmartDeviceLink 2.0
 */
public enum ComponentVolumeStatus {
	/**
	 * Unknown
	 */
	UNKNOWN,
	/**
	 * Normal
	 */
	NORMAL,
	/**
	 * Low
	 */
	LOW,
	/**
	 * Fault
	 */
	FAULT,
	/**
	 * Alert
	 */
	ALERT,
	/**
	 * Not supported
	 */
	NOT_SUPPORTED;

    /**
     * Convert String to ComponentVolumeStatus
     * @param value String
     * @return ComponentVolumeStatus
     */      	
    public static ComponentVolumeStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
