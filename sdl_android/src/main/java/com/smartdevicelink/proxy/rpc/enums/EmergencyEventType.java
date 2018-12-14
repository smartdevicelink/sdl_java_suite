package com.smartdevicelink.proxy.rpc.enums;
/**
 * 
 * Reflects the emergency event status of the vehicle.
 *
 */
public enum EmergencyEventType {
	/** No emergency event has happened.
	 */
    NO_EVENT,
    /** Frontal collision has happened.
     */
    FRONTAL,
    /** Side collision has happened.
     */
    SIDE,
    /**Rear collision has happened.
     */
    REAR,
    /** A rollover event has happened.
     */
    ROLLOVER,
    /** The signal is not supported
     */
    NOT_SUPPORTED,
    /** Emergency status cannot be determined
     */
    FAULT;
    /**
     * Convert String to EmergencyEventType
     * @param value String
     * @return EmergencyEventTpe
     */
    public static EmergencyEventType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
