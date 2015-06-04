package com.smartdevicelink.rpc.enums;

/**
 * Properties of a user-initiated VR interaction (i.e. interactions started by the user pressing the PTT button).
 * @since SmartDeviceLink 1.0
 */
public enum GlobalProperty{
	/**
	 * The help prompt to be spoken if the user needs assistance during a user-initiated interaction.
	 */
    HELP_PROMPT,
    /**
     * The prompt to be spoken if the user-initiated interaction times out waiting for the user's verbal input.
     */
    TIMEOUT_PROMPT,
    VR_HELP_TITLE,
    VR_HELP_ITEMS,
    MENU_NAME,
    MENU_ICON,
    KEYBOARD_PROPERTIES;

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
