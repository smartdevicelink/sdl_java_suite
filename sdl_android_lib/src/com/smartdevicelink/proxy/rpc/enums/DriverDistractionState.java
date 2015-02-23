/**
 * 
 */
package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration that describes possible states of driver distraction.
 * @since SmartDeviceLink 1.0
 */
public enum DriverDistractionState {
	/**
	 * Driver distraction rules are in effect.
	 */
	DD_ON,
	/**
	 * Driver distraction rules are NOT in effect.
	 */
	DD_OFF;
	
	/**
	 * Convert String to DriverDistractionState
	 * @param value String
	 * @return DriverDistractionState
	 */
	public static DriverDistractionState valueForString(String value) {
    	try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
