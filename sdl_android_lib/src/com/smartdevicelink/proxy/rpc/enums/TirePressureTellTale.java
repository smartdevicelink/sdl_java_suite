package com.smartdevicelink.proxy.rpc.enums;

public enum TirePressureTellTale {
    OFF,
    ON,
    FLASH;

    public static TirePressureTellTale valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
