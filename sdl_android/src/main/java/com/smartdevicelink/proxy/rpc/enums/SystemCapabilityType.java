package com.smartdevicelink.proxy.rpc.enums;

/**
 * The SystemCapabilityType indicates which type of capability information exists in a SystemCapability struct.
 */

public enum SystemCapabilityType {
    NAVIGATION,

    PHONE_CALL,

    VIDEO_STREAMING,

    REMOTE_CONTROL,

    /* These below aren’t actually part of the RPC spec. Only for Internal Proxy use */

    HMI,

    DISPLAY,

    AUDIO_PASSTHROUGH,

    BUTTON,

    HMI_ZONE,

    PRESET_BANK,

    SOFTBUTTON,

    SPEECH;

    public static SystemCapabilityType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
