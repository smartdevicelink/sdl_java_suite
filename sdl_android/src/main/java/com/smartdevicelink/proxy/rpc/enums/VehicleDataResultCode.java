package com.smartdevicelink.proxy.rpc.enums;
/** Enumeration that describes possible result codes of a vehicle data entry request.
 * 
 * @since SmartDeviceLink 2.0 
 *
 *@see DIDResult
 *@see ReadDID
 */

public enum VehicleDataResultCode {
	/**Individual vehicle data item / DTC / DID request or subscription successful
	 * 
	 */

	SUCCESS,
	/**
     *DTC / DID request successful, however, not all active DTCs or full contents of DID location available
     * 
     * @since SmartDeviceLink 4.0
     */
	
	TRUNCATED_DATA,
	/** This vehicle data item is not allowed for this app .The request is not authorized in local policies.
	 * 
	 */

	DISALLOWED,
	/** The user has not granted access to this type of vehicle data item at this time.
	 * 
	 */

	USER_DISALLOWED,
	/** The ECU ID referenced is not a valid ID on the bus / system.
	 * 
	 */

	INVALID_ID,
	/** The requested vehicle data item / DTC / DID is not currently available or responding on the bus / system.
	 * 
	 */

	VEHICLE_DATA_NOT_AVAILABLE,
	/** The vehicle data item is already subscribed.
	 * 
	 */

	DATA_ALREADY_SUBSCRIBED,
	/** The vehicle data item cannot be unsubscribed because it is not currently subscribed.
	 * 
	 */

	DATA_NOT_SUBSCRIBED,
	/** The request for this item is ignored because it is already in progress
 	*/

	IGNORED;

    public static VehicleDataResultCode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
