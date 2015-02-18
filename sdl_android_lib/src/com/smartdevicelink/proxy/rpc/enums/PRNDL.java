package com.smartdevicelink.proxy.rpc.enums;

/**
 * The selected gear.
 * @since SmartDeviceLink 2.0
 */
public enum PRNDL {
	/**
	 * Parking
	 */
    PARK,
    /**
     * Reverse gear
     */
    REVERSE,
    /**
     * No gear
     */
    NEUTRAL,
    DRIVE,
    /**
     * Drive Sport mode
     */
    SPORT,
    /**
     * 1st gear hold
     */
    LOWGEAR,
    /**
     * First gear
     */
    FIRST,
    /**
     * Second gear
     */
    SECOND,
    /**
     * Third gear
     */
    THIRD,
    /**
     * Fourth gear
     */
    FOURTH,
    /**
     * Fifth gear
     */
    FIFTH,
    /**
     * Sixth gear
     */
    SIXTH,
    SEVENTH,
    EIGHTH,
    UNKNOWN,
    FAULT;

    /**
     * Convert String to PRNDL
     * @param value String
     * @return PRNDL
     */         
    public static PRNDL valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
