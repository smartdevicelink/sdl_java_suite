package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the reported battery status of the connected device, if reported.
 * @since SmartDeviceLink 2.0
 */
public enum DeviceLevelStatus implements JsonName{
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
	NOT_PROVIDED,
	
	;

    /**
     * Convert String to DeviceLevelStatus
     * @param value String
     * @return DeviceLevelStatus
     */	
    public static DeviceLevelStatus valueForString(String value) {
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
    public static DeviceLevelStatus valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
