package com.smartdevicelink.proxy.rpc.enums;

public enum DefrostZone {
    FRONT,
    REAR,
    ALL,
    NONE,
    ;

    public static DefrostZone valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
