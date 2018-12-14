package com.smartdevicelink.proxy.rpc.enums;

public enum FuelType {
    GASOLINE,
    DIESEL,
    /**
     * For vehicles using compressed natural gas.
     */
    CNG,
    /**
     * For vehicles using liquefied petroleum gas.
     */
    LPG,
    /**
     * For FCEV (fuel cell electric vehicle).
     */
    HYDROGEN,
    /**
     * For BEV (Battery Electric Vehicle), PHEV (Plug-in Hybrid Electric Vehicle), solar vehicles and other vehicles which run on a battery.
     */
    BATTERY,
    ;

    public static FuelType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
