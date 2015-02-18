package com.smartdevicelink.proxy.rpc.enums;

public enum VehicleDataActiveStatus {
	INACTIVE_NOT_CONFIRMED,
	INACTIVE_CONFIRMED,
	ACTIVE_NOT_CONFIRMED,
	ACTIVE_CONFIRMED,
    FAULT;

    public static VehicleDataActiveStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
