package com.smartdevicelink.proxy.rpc.enums;

public enum TirePressureTellTale {
    OFF,
    ON,
    FLASH;

    public static TirePressureTellTale valueForString(String value) {
        return valueOf(value);
    }
}
