package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible keyboard layouts.
 * 
 *
 */
public enum KeyboardLayout {
    QWERTY,
    QWERTZ,
    AZERTY;

    public static KeyboardLayout valueForString(String value) {
        return valueOf(value);
    }
}