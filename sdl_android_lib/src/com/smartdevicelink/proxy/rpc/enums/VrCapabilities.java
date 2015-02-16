package com.smartdevicelink.proxy.rpc.enums;

/**
 * The VR capabilities of the connected SDL platform.
 * 
 */
public enum VrCapabilities {
	/**
	 * The SDL platform is capable of recognizing spoken text in the current
	 * language.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
    @Deprecated
	Text,

    /**
     * The SDL platform is capable of recognizing spoken text in the current
     * language.
     * 
     * @since SmartDeviceLink 3.0
     */
	TEXT,
	;

    public static VrCapabilities valueForString(String value) {
        if (value.equalsIgnoreCase(TEXT.toString()))
        {
        	return TEXT;
        }
        return valueOf(value);
    }
}
