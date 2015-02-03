package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the status of a vehicle data event; e.g. a seat belt event status.
 * @since SmartDeviceLink 2.0
 */
public enum VehicleDataEventStatus implements JsonName{
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
	FAULT,
	
	;
	
    /**
     * Convert String to VehicleDataEventStatus
     * @param value String
     * @return VehicleDataEventStatus
     */   	
    public static VehicleDataEventStatus valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static VehicleDataEventStatus valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }

        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
}
