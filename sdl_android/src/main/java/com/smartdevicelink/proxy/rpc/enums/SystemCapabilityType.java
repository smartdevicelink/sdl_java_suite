package com.smartdevicelink.proxy.rpc.enums;

/**
 * The SystemCapabilityType indicates which type of capability information exists in a SystemCapability struct.
 */

public enum SystemCapabilityType {
    NAVIGATION,

    PHONE_CALL,

    VIDEO_STREAMING;

    REMOTE_CONTROL,
    ;


    public static SystemCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
