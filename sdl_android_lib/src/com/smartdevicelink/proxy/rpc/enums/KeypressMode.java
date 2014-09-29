package com.smartdevicelink.proxy.rpc.enums;

public enum KeypressMode {
    SINGLE_KEYPRESS,
    QUEUE_KEYPRESSES,
    RESEND_CURRENT_ENTRY;

    public static KeypressMode valueForString(String value) {
        return valueOf(value);
    }
}