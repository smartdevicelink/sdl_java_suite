package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible keyboard events.
 * 
 *
 */
public enum KeypressMode {
	/**
	 * Each keypress is individually sent as the user presses the keyboard keys.
	 */
    SINGLE_KEYPRESS,
    /**
     * The keypresses are queued and a string is eventually sent once the user chooses to submit their entry.
     */
    QUEUE_KEYPRESSES,
    /**
     * The keypresses are queue and a string is sent each time the user presses a keyboard key; the string contains the entire current entry.
     */
    RESEND_CURRENT_ENTRY;

    public static KeypressMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}