package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The list of potential compass directions
 * @since SmartDeviceLink 2.0 
 */
public enum CompassDirection implements JsonName{
	/**
	 * Direction North
	 */
	NORTH,
	/**
	 * Direction Northwest
	 */
	NORTHWEST,
	/**
	 * Direction West
	 */
	WEST,
	/**
	 * Direction Southwest
	 */
	SOUTHWEST,
	/**
	 * Direction South
	 */
	SOUTH,
	/**
	 * Direction Southeast
	 */
	SOUTHEAST,
	/**
	 * Direction East
	 */
	EAST,
	/**
	 * Direction Northeast
	 */
	NORTHEAST,
	
	;

    /**
     * Convert String to CompassDirection
     * @param value String
     * @return CompassDirection
     */
    public static CompassDirection valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static CompassDirection valueForJsonName(String name, int sdlVersion){
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
