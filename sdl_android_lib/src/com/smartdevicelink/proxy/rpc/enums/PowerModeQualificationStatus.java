package com.smartdevicelink.proxy.rpc.enums;

public enum PowerModeQualificationStatus {
	POWER_MODE_UNDEFINED,
	POWER_MODE_EVALUATION_IN_PROGRESS,
	NOT_DEFINED,
	POWER_MODE_OK;

    public static PowerModeQualificationStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
