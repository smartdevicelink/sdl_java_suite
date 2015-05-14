package com.smartdevicelink.proxy.rpc.enums;

public enum KeyboardLayout {
    QWERTY,
    QWERTZ,
    AZERTY;

    public static KeyboardLayout valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}