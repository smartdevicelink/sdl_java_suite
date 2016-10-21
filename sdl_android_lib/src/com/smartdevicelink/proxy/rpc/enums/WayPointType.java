package com.smartdevicelink.proxy.rpc.enums;

public enum WayPointType {
    ALL,
    DESTINATION,
    ;

    public static WayPointType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
