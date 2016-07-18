package com.smartdevicelink.proxy.rpc.enums;
/** Enumeration that describes system actions that can be triggered.
 * 
 * @since SmartDeviceLink 2.0
 *
 */

public enum SystemAction {
	/** Default action occurs.  Standard behavior (e.g. SoftButton clears overlay).
	 * 
	 */

	DEFAULT_ACTION,
	/** App is brought into HMI_FULL.
	 * 
	 */

	STEAL_FOCUS,
	/** Current system context is maintained. An overlay is persisted even though a SoftButton has been pressed and the notification sent.
	 * 
	 */

	KEEP_CONTEXT;
	/**
     * Convert String to SystemAction
     * @param value String
     * @return SystemAction
     */ 

    public static SystemAction valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
