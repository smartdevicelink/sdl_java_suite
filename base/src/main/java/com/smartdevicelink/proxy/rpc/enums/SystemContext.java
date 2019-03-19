package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Indicates whether or not a user-initiated interaction is in progress, and if
 * so, in what mode (i.e. MENU or VR).
 * 
 * @since SmartDeviceLink 1.0
 */
public enum SystemContext {
	/**
	 * No user interaction (user-initiated or app-initiated) is in progress.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	SYSCTXT_MAIN("MAIN"),
	/**
	 * VR-oriented, user-initiated or app-initiated interaction is in-progress.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	SYSCTXT_VRSESSION("VRSESSION"),
	/**
	 * Menu-oriented, user-initiated or app-initiated interaction is
	 * in-progress.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	SYSCTXT_MENU("MENU"),
	/**
	 * The app's display HMI is currently being obscured by either a system or
	 * other app's overlay.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	SYSCTXT_HMI_OBSCURED("HMI_OBSCURED"),
	/**
	 * Broadcast only to whichever app has an alert currently being displayed.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	SYSCTXT_ALERT("ALERT");

	private final String INTERNAL_NAME;
    
    private SystemContext(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    public static SystemContext valueForString(String value) {
        if(value == null){
            return null;
        }
        
        for (SystemContext anEnum : EnumSet.allOf(SystemContext.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
