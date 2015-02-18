package com.smartdevicelink.proxy.rpc.enums;

public enum TouchType {
    BEGIN,
    MOVE,
    END;

    public static TouchType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}