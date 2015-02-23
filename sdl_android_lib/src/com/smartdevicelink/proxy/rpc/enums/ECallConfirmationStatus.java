package com.smartdevicelink.proxy.rpc.enums;

public enum ECallConfirmationStatus {
    NORMAL,
    CALL_IN_PROGRESS,
    CALL_CANCELLED,
    CALL_COMPLETED,
    CALL_UNSUCCESSFUL,
    ECALL_CONFIGURED_OFF,
    CALL_COMPLETE_DTMF_TIMEOUT;

    public static ECallConfirmationStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
