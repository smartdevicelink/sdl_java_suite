package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the reported battery status of the connected device, if reported.
 * @since SmartDeviceLink 2.0
 */
public enum DeviceLevelStatus {
	/**
	 * Zero level bars
	 */
	ZERO_LEVEL_BARS,
	/**
	 * One level bars
	 */
	ONE_LEVEL_BARS,
	/**
	 * Two level bars
	 */
	TWO_LEVEL_BARS,
	/**
	 * Three level bars
	 */
	THREE_LEVEL_BARS,
	/**
	 * Four level bars
	 */
	FOUR_LEVEL_BARS,
	/**
	 * Not provided
	 */
	NOT_PROVIDED;

    /**
     * Convert String to DeviceLevelStatus
     * @param value String
     * @return DeviceLevelStatus
     */	
    public static DeviceLevelStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
