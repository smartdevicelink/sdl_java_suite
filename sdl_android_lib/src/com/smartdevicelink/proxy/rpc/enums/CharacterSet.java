package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Character sets supported by SDL.
 * @since SmartDeviceLink 1.0
 */
public enum CharacterSet implements JsonName{
    TYPE2SET,
    TYPE5SET,
    CID1SET,
    CID2SET,
    
    ;

    /**
     * Convert String to CharacterSet
     * @param value String
     * @return CharacterSet
     */
    public static CharacterSet valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static CharacterSet valueForJsonName(String name, int sdlVersion){
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
