package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Properties of a user-initiated VR interaction (i.e. interactions started by the user pressing the PTT button).
 * @since SmartDeviceLink 1.0
 */
public enum GlobalProperty implements JsonName{
	/**
	 * The help prompt to be spoken if the user needs assistance during a user-initiated interaction.
	 */
    HELPPROMPT,
    /**
     * The prompt to be spoken if the user-initiated interaction times out waiting for the user's verbal input.
     */
    TIMEOUTPROMPT,
    VRHELPTITLE,
    VRHELPITEMS,
    MENUNAME,
    MENUICON,
    KEYBOARDPROPERTIES,
    
    ;

    /**
     * Convert String to GlobalProperty
     * @param value String
     * @return GlobalProperty
     */
    public static GlobalProperty valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static GlobalProperty valueForJsonName(String name, int sdlVersion){
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
