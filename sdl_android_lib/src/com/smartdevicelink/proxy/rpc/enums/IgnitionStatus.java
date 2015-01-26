package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the status of ignition..
 * @since SmartDeviceLink 2.0
 */
public enum IgnitionStatus implements JsonName {
	/**
	 * Ignition status currently unknown
	 */
	UNKNOWN,
	/**
	 * Ignition is off
	 */
	OFF,
	/**
	 * Ignition is in mode accessory
	 */
	ACCESSORY,
	/**
	 * Ignition is in mode run
	 */
	RUN,
	/**
	 * Ignition is in mode run
	 */
	START,
	/**
	 * Signal is invalid
	 */
	INVALID,
	
	;

    /**
     * Convert String to IgnitionStatus
     * @param value String
     * @return IgnitionStatus
     */   	
    public static IgnitionStatus valueForString(String value) {
        return valueOf(value);
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static IgnitionStatus valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
