package com.smartdevicelink.proxy.rpc.enums;

public enum HybridAppPreference {

    MOBILE,
    CLOUD,
    BOTH;

    public static HybridAppPreference valueForString(String value){
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
