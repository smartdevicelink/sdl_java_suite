package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the current power mode qualification.
 * 
 * @since SmartDeviceLink 2.0
 *
 */

public enum PowerModeQualificationStatus {
	/** The power mode of the vehicle is currently considered undefined
	 * 
	 */

	POWER_MODE_UNDEFINED,
	/** The evaluation of the power mode is in progress
	 * 
	 */

	POWER_MODE_EVALUATION_IN_PROGRESS,
	/** Currently undefined
	 * 
	 */

	NOT_DEFINED,
	/** The power mode of the vehicle
	 *
	 */

	POWER_MODE_OK;
	/**
     * Convert String to PowerModeQualificationStatus
     * @param value String
     * @return PowerModeQualificationStatus
     */ 

    public static PowerModeQualificationStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
