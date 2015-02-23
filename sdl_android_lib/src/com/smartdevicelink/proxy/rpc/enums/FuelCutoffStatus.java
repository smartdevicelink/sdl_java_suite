package com.smartdevicelink.proxy.rpc.enums;

public enum FuelCutoffStatus {
	TERMINATE_FUEL,
	NORMAL_OPERATION,
    FAULT;

    public static FuelCutoffStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
