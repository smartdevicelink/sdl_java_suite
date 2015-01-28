package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Contains information about the type of image.
 * @since SmartDeviceLink 2.0 
 */
public enum ImageType implements JsonName{
	/**
	 * Just the static hex icon value to be used
	 */
    STATIC,
    /**
     * Binary image file to be used (identifier to be sent by PutFile)
     */
    DYNAMIC,
    
    ;

    /**
     * Convert String to ImageType
     * @param value String
     * @return ImageType
     */
    public static ImageType valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static ImageType valueForJsonName(String name, int sdlVersion){
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
