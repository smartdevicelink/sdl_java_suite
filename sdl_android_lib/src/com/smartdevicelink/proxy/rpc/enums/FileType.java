package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Enumeration listing possible file tpyes.
 * @since SmartDeviceLink 2.0
 */
public enum FileType implements JsonName {
	/**
	 * BMP
	 */
    GRAPHIC_BMP,
    /**
     * JPEG
     */
    GRAPHIC_JPEG,
    /**
     * PNG
     */
    GRAPHIC_PNG,
    /**
     * WAVE
     */
    AUDIO_WAVE,
    AUDIO_AAC,
    /**
     * MP3
     */
    AUDIO_MP3,
    BINARY,
    JSON,
    
    ;

    /**
     * Convert String to FileType
     * @param value String
     * @return FileType
     */      
    public static FileType valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static FileType valueForJsonName(String name, int sdlVersion){
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
