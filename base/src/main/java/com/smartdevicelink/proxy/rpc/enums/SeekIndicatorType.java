package com.smartdevicelink.proxy.rpc.enums;

public enum SeekIndicatorType {
    TRACK,
    TIME;

    public static SeekIndicatorType valueForString(String value) {
        try {
            return valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
}