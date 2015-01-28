package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Indicates whether or not a user-initiated interaction is in progress, and if
 * so, in what mode (i.e. MENU or VR).
 * 
 * @since SmartDeviceLink 1.0
 */
public enum SystemContext implements JsonName{
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
	SYSCTXT_ALERT("ALERT"),
	
	;

    String internalName;
    
    private SystemContext(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    public static SystemContext valueForString(String value) {
        for (SystemContext anEnum : EnumSet.allOf(SystemContext.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static SystemContext valueForJsonName(String name, int sdlVersion){
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
            return this.internalName;
        }
    }
}
