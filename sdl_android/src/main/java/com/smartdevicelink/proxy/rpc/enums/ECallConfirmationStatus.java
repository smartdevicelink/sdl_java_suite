package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the eCall Notification.
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 *
 */

public enum ECallConfirmationStatus {
	/**
	 * No E-Call signal triggered.
	 */
    NORMAL,
    /**
     * An E-Call is being in progress.
     */
    CALL_IN_PROGRESS,
    /**
     * An E-Call was cancelled by the user.
     */
    CALL_CANCELLED,
    /**
     * The E-Call sequence is completed.
     */
    CALL_COMPLETED,
    /**
     * An E-Call could not be connected.
     */
    CALL_UNSUCCESSFUL,
    /**
     * E-Call is not configured on this vehicle.
     */
    ECALL_CONFIGURED_OFF,
    /**
     * E-Call is considered to be complete without Emergency Operator contact.
     */
    CALL_COMPLETE_DTMF_TIMEOUT;

    public static ECallConfirmationStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
