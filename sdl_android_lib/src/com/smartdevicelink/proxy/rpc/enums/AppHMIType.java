package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Enumeration listing possible app hmi types.
 * @since SmartDeviceLink 2.0
 */
public enum AppHMIType implements JsonName{
	/**
	 * The App will have default rights.
	 */
    DEFAULT,
    /**
     * Communication type of App
     */
    COMMUNICATION,
    /**
     * App dealing with Media
     */
    MEDIA,
    /**
     * Messaging App
     */
    MESSAGING,
    /**
     * Navigation App
     */
    NAVIGATION,
    /**
     * Information App
     */
    INFORMATION,
    /**
     * App dealing with social media
     */
    SOCIAL,
    BACKGROUND_PROCESS,
    /**
     * App only for Testing purposes
     */
    TESTING,
    /**
     * System App
     */
    SYSTEM,
    
    ;

    /**
     * Convert String to AppHMIType
     * @param value String
     * @return AppHMIType
     */      
    public static AppHMIType valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static AppHMIType valueForJsonName(String name, int sdlVersion){
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
