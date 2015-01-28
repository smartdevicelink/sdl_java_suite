package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Indicates whether the button was depressed or released. A BUTTONUP event will
 * always be preceded by a BUTTONDOWN event
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum ButtonEventMode implements JsonName{
	/**
	 * The button was released
	 */
	BUTTONUP,
	/**
	 * The button was depressed
	 */
	BUTTONDOWN,
	
	;

	/**
	 * Returns a ButtonEventMode (BUTTONUP or BUTTONDOWN)
	 * 
	 * @param value
	 *            a String
	 * @return ButtonEventMode -BUTTONUP or BUTTONDOWN
	 */

    public static ButtonEventMode valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static ButtonEventMode valueForJsonName(String name, int sdlVersion){
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
