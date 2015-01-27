package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The selected gear.
 * @since SmartDeviceLink 2.0
 */
public enum PRNDL implements JsonName {
	/**
	 * Parking
	 */
    PARK,
    /**
     * Reverse gear
     */
    REVERSE,
    /**
     * No gear
     */
    NEUTRAL,
    DRIVE,
    /**
     * Drive Sport mode
     */
    SPORT,
    /**
     * 1st gear hold
     */
    LOWGEAR,
    /**
     * First gear
     */
    FIRST,
    /**
     * Second gear
     */
    SECOND,
    /**
     * Third gear
     */
    THIRD,
    /**
     * Fourth gear
     */
    FOURTH,
    /**
     * Fifth gear
     */
    FIFTH,
    /**
     * Sixth gear
     */
    SIXTH,
    SEVENTH,
    EIGHTH,
    UNKNOWN,
    FAULT,
    
    ;

    /**
     * Convert String to PRNDL
     * @param value String
     * @return PRNDL
     */         
    public static PRNDL valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static PRNDL valueForJsonName(String name, int sdlVersion){
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
