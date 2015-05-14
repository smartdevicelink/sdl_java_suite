package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration listing possible app hmi types.
 * @since SmartDeviceLink 2.0
 */
public enum AppHMIType {
	/**
	 * The App will have default rights.
	 */
    DEFAULT,
    /**
     * Communication type of App
     */
    COMMUNICATION,
    /**
     * App dealing with Media
     */
    MEDIA,
    /**
     * Messaging App
     */
    MESSAGING,
    /**
     * Navigation App
     */
    NAVIGATION,
    /**
     * Information App
     */
    INFORMATION,
    /**
     * App dealing with social media
     */
    SOCIAL,
    BACKGROUND_PROCESS,
    /**
     * App only for Testing purposes
     */
    TESTING,
    /**
     * System App
     */
    SYSTEM;

    /**
     * Convert String to AppHMIType
     * @param value String
     * @return AppHMIType
     */      
    public static AppHMIType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
