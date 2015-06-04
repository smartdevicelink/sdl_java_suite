package com.smartdevicelink.proxy.rpc.enums;

public enum MessageType {
    REQUEST,
    RESPONSE,
    NOTIFICATION;

    public static MessageType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
