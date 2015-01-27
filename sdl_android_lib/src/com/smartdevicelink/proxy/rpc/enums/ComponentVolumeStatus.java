package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The volume status of a vehicle component
 * @since SmartDeviceLink 2.0
 */
public enum ComponentVolumeStatus implements JsonName {
	/**
	 * Unknown
	 */
	UNKNOWN,
	/**
	 * Normal
	 */
	NORMAL,
	/**
	 * Low
	 */
	LOW,
	/**
	 * Fault
	 */
	FAULT,
	/**
	 * Alert
	 */
	ALERT,
	/**
	 * Not supported
	 */
	NOT_SUPPORTED,
	
	;

    /**
     * Convert String to ComponentVolumeStatus
     * @param value String
     * @return ComponentVolumeStatus
     */      	
    public static ComponentVolumeStatus valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static ComponentVolumeStatus valueForJsonName(String name, int sdlVersion){
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
