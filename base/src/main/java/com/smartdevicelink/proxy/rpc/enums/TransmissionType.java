package com.smartdevicelink.proxy.rpc.enums;

public enum TransmissionType {
    /**
     * Manual transmission
     */
    MANUAL,

    /**
     * Automatic transmission
     */
    AUTOMATIC,

    /**
     * Semi automatic transmission
     */
    SEMI_AUTOMATIC,

    /**
     * Dual clutch transmission
     */
    DUAL_CLUTCH,
    /**
     * Continuously variable transmission(CVT)
     */
    CONTINUOUSLY_VARIABLE,

    /**
     * Infinitely variable transmission
     */
    INFINITELY_VARIABLE,

    /**
     * Electric variable transmission
     */
    ELECTRIC_VARIABLE,
    /**
     * Direct drive between engine and wheels
     */
	DIRECT_DRIVE;

    /**
     * Convert String to PRNDL
     * @param value String
     * @return PRNDL
     */
    public static TransmissionType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
