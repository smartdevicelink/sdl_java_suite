package com.smartdevicelink.proxy.rpc.enums;

public enum VehicleDataResultCode {
	SUCCESS,
	/**
     *DTC / DID request successful, however, not all active DTCs or full contents of DID location available
     * 
     * @since SmartDeviceLink 4.0
     */
	TRUNCATED_DATA,
	DISALLOWED,
	USER_DISALLOWED,
	INVALID_ID,
	VEHICLE_DATA_NOT_AVAILABLE,
	DATA_ALREADY_SUBSCRIBED,
	DATA_NOT_SUBSCRIBED,
	IGNORED;

    public static VehicleDataResultCode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
