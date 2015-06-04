package com.smartdevicelink.rpc.enums;

public enum MaintenanceModeStatus {
	NORMAL,
	NEAR,
	ACTIVE,
	FEATURE_NOT_PRESENT;

    public static MaintenanceModeStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
