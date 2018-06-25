package com.smartdevicelink.proxy.rpc.enums;

public enum TemperatureUnit {
    CELSIUS,
    FAHRENHEIT;

    public static TemperatureUnit valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
