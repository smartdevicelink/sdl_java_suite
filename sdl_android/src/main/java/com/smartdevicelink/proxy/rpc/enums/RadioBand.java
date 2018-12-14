package com.smartdevicelink.proxy.rpc.enums;

public enum RadioBand {
    AM,
    FM,
    XM,
    ;

    public static RadioBand valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
