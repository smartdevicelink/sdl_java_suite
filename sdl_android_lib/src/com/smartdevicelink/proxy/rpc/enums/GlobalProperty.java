package com.smartdevicelink.proxy.rpc.enums;

/**
 * Properties of a user-initiated VR interaction (i.e. interactions started by the user pressing the PTT button).
 * @since SmartDeviceLink 1.0
 */
public enum GlobalProperty{
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
    KEYBOARDPROPERTIES;

    /**
     * Convert String to GlobalProperty
     * @param value String
     * @return GlobalProperty
     */
    public static GlobalProperty valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
