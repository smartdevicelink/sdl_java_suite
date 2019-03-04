package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible keyboard layouts.
 * 
 * @since SmartDeviceLink 2.3.2
 */
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