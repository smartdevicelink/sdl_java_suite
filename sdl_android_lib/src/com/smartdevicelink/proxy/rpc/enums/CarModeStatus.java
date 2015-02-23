package com.smartdevicelink.proxy.rpc.enums;

public enum CarModeStatus {
	NORMAL,
	FACTORY,
	TRANSPORT,
	CRASH;

    public static CarModeStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
