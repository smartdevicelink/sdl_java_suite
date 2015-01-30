package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Specifies HMI Zones in the vehicle.
 * 
 */
public enum HmiZoneCapabilities implements JsonName{
	/**
	 * Indicates HMI available for front seat passengers.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	FRONT,
	/**
	 * Indicates HMI available for rear seat passengers.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	BACK,
	
	;

    public static HmiZoneCapabilities valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static HmiZoneCapabilities valueForJsonName(String name, int sdlVersion){
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
