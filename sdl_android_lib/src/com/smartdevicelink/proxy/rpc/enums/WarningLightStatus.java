package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the status of a cluster instrument warning light.
 * @since SmartDeviceLink 2.0
 */
public enum WarningLightStatus implements JsonName{
	/**
	 * Warninglight Off
	 */
	OFF,
	/**
	 * Warninglight On
	 */
	ON,
	/**
	 * Warninglight is flashing
	 */
	FLASH,
	NOT_USED,
	
	;

    /**
     * Convert String to WarningLightStatus
     * @param value String
     * @return WarningLightStatus
     */    
    public static WarningLightStatus valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static WarningLightStatus valueForJsonName(String name, int sdlVersion){
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
