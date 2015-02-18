package com.smartdevicelink.proxy.rpc.enums;

public enum KeypressMode {
    SINGLE_KEYPRESS,
    QUEUE_KEYPRESSES,
    RESEND_CURRENT_ENTRY;

    public static KeypressMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}