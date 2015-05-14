package com.smartdevicelink.proxy.rpc.enums;

public enum LockScreenStatus {
	REQUIRED,
	OPTIONAL,
	OFF;
	
    public static LockScreenStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
