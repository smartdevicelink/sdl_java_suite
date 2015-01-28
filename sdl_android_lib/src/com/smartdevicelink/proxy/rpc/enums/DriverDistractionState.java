/**
 * 
 */
package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Enumeration that describes possible states of driver distraction.
 * @since SmartDeviceLink 1.0
 */
public enum DriverDistractionState implements JsonName{
	/**
	 * Driver distraction rules are in effect.
	 */
	DD_ON,
	/**
	 * Driver distraction rules are NOT in effect.
	 */
	DD_OFF,
	
	;
	
	/**
	 * Convert String to DriverDistractionState
	 * @param value String
	 * @return DriverDistractionState
	 */
	public static DriverDistractionState valueForString(String value) {
    	return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static DriverDistractionState valueForJsonName(String name, int sdlVersion){
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
