package com.smartdevicelink.proxy.rpc.enums;

/**
 * @since SmartDeviceLink 7.0
 * Enumerations of all available app capability types
 */
public enum AppCapabilityType {
    VIDEO_STREAMING,
    ;

    /**
     * Convert String to AppCapabilityType
     * @param value String
     * @return AppCapabilityType
     */
    public static AppCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
