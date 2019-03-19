package com.smartdevicelink.proxy.rpc.enums;

public enum VentilationMode {
    UPPER,
    LOWER,
    BOTH,
    NONE,
    ;

    public static VentilationMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
