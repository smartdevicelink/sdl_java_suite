package com.smartdevicelink.proxy.rpc.enums;

public enum KeyboardEvent {

    KEYPRESS,
    ENTRY_SUBMITTED,
    ENTRY_CANCELLED,
    ENTRY_ABORTED;

    public static KeyboardEvent valueForString(String value) {
        return valueOf(value);
    }

}
