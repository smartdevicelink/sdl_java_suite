package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The supported dimensions of the GPS.
 * @since SmartDeviceLink 2.0
 */
public enum Dimension implements JsonName{
	/**
	 * No GPS at all
	 */
    NO_FIX("NO_FIX"),
    /**
     * Longitude and latitude
     */
    _2D("2D"),
    /**
     * Longitude and latitude and altitude
     */
    _3D("3D");
    
    String internalName;

    private Dimension(String internalName) {
    	this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    /**
     * Convert String to Dimension
     * @param value String
     * @return Dimension
     */    
    public static Dimension valueForString(String value) {
    	for (Dimension anEnum : EnumSet.allOf(Dimension.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static Dimension valueForJsonName(String name, int sdlVersion){
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
            return this.internalName;
        }
    }
}
