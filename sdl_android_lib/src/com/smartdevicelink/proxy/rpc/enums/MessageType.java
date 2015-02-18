package com.smartdevicelink.proxy.rpc.enums;

public enum MessageType {
    request,
    response,
    notification;

    public static MessageType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
