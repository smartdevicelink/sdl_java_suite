package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the ignition switch stability.
 * @since SmartDeviceLink 2.0
 */
public enum IgnitionStableStatus {
	/**
	 * The current ignition switch status is considered not to be stable.
	 */
	IGNITION_SWITCH_NOT_STABLE,
	/**
	 * The current ignition switch status is considered to be stable.
	 */
	IGNITION_SWITCH_STABLE,
	MISSING_FROM_TRANSMITTER;

    /**
     * Convert String to IgnitionStableStatus
     * @param value String
     * @return IgnitionStableStatus
     */    	
    public static IgnitionStableStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
