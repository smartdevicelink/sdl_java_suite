package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible keyboard events.
 * 
 *
 */
public enum KeyboardEvent {
	/** The use has pressed the keyboard key (applies to both SINGLE_KEYPRESS and RESEND_CURRENT_ENTRY modes).
	 * 
	 */

    KEYPRESS,
    /** The User has finished entering text from the keyboard and submitted the entry.
     * 
     */

    ENTRY_SUBMITTED,
    /** The User has pressed the HMI-defined "Cancel" button.
     * 
     */

    ENTRY_CANCELLED,
    /** The User has not finished entering text and the keyboard is aborted with the event of higher priority.
     *   
     */
    ENTRY_ABORTED,
    /** 
     * @since SmartDeviceLink 4.0
     */
    ENTRY_VOICE,
    ;
    /**
     * Convert String to KeyboardEvent
     * @param value String
     * @return KeyboardEvent
     */ 

    public static KeyboardEvent valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }

}
