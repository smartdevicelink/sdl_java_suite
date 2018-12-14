package com.smartdevicelink.proxy.rpc.enums;

/**
 * Indicates whether the button was depressed or released. A BUTTONUP event will
 * always be preceded by a BUTTONDOWN event
 * 
 * @see SoftButtonCapabilities
 * @see OnButtonEvent
 * @since SmartDeviceLink 1.0
 */
public enum ButtonEventMode {
	/**
	 * The button was released
	 */
	BUTTONUP,
	/**
	 * The button was depressed
	 */
	BUTTONDOWN;

	/**
	 * Returns a ButtonEventMode (BUTTONUP or BUTTONDOWN)
	 * 
	 * @param value
	 *            a String
	 * @return ButtonEventMode -BUTTONUP or BUTTONDOWN
	 */

    public static ButtonEventMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
