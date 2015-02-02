package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The list of possible alignments of text in a field.
 * @since SmartDeviceLink 1.0
 */
public enum TextAlignment implements JsonName{
	/**
	 * Text aligned left.
	 */
    LEFT_ALIGNED,
    /**
     * Text aligned right.
     */
    RIGHT_ALIGNED,
    /**
     * Text aligned centered.
     */
    CENTERED,
    
    ;

    /**
     * Convert String to TextAlignment
     * @param value String
     * @return TextAlignment
     */
    public static TextAlignment valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static TextAlignment valueForJsonName(String name, int sdlVersion){
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
