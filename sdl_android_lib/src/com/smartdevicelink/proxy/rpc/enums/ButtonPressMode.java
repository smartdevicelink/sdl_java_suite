package com.smartdevicelink.proxy.rpc.enums;

/**
 * Indicates whether this is a LONG or SHORT button press.
 * <p></p>
 *@see ButtonEventMode
 *@since SmartDeviceLink 1.0
 */
public enum ButtonPressMode {
	/**
	 * The button has been depressed for 2 seconds. The button may remain
	 * depressed after receiving this event
	 */
	LONG,
	/**
	 * The button was released before the 2-second long-press interval had
	 * elapsed
	 */
	SHORT;
	/**
	 * Returns a ButtonPressMode (LONG or SHORT)
	 * 
	 * @param value
	 *            a String
	 * @return ButtonPressMode -LONG or SHORT
	 */

    public static ButtonPressMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
