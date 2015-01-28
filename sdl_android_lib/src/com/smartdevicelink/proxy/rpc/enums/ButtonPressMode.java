package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Indicates whether this is a LONG or SHORT button press
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum ButtonPressMode implements JsonName{
	/**
	 * The button has been depressed for 2 seconds. The button may remain
	 * depressed after receiving this event
	 */
	LONG,
	/**
	 * The button was released before the 2-second long-press interval had
	 * elapsed
	 */
	SHORT,
	
	;
	
	/**
	 * Returns a ButtonPressMode (LONG or SHORT)
	 * 
	 * @param value
	 *            a String
	 * @return ButtonPressMode -LONG or SHORT
	 */

    public static ButtonPressMode valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static ButtonPressMode valueForJsonName(String name, int sdlVersion){
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
