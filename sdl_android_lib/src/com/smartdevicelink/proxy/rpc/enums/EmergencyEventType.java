package com.smartdevicelink.proxy.rpc.enums;

public enum EmergencyEventType {

    NO_EVENT,
    FRONTAL,
    SIDE,
    REAR,
    ROLLOVER,
    NOT_SUPPORTED,
    FAULT;

    public static EmergencyEventType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
