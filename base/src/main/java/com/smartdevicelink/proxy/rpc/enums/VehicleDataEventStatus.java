package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the status of a vehicle data event; e.g. a seat belt event status.
 * @since SmartDeviceLink 2.0
 */
public enum VehicleDataEventStatus {
	/**
	 * No event available
	 */
	NO_EVENT,
	NO,
	YES,
	/**
	 * Vehicle data event is not support 
	 */
	NOT_SUPPORTED,
	FAULT;

    /**
     * Convert String to VehicleDataEventStatus
     * @param value String
     * @return VehicleDataEventStatus
     */   	
    public static VehicleDataEventStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
