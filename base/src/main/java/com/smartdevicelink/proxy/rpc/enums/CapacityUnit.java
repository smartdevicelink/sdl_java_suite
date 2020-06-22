package com.smartdevicelink.proxy.rpc.enums;

public enum CapacityUnit {
    LITERS,

    KILOWATTHOURS,

    KILOGRAMS;

    public static CapacityUnit valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
