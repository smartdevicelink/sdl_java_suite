package com.smartdevicelink.proxy.rpc.enums;

public enum KeyboardEvent {

    KEYPRESS,
    ENTRY_SUBMITTED,
    ENTRY_CANCELLED,
    ENTRY_ABORTED,
    /** 
     * @since SmartDeviceLink 4.0
     */
    ENTRY_VOICE,
    ;

    public static KeyboardEvent valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }

}
