package com.smartdevicelink.proxy.rpc.enums;

public enum LockScreenStatus {
	REQUIRED,
	OPTIONAL,
	OFF;
	
    public static LockScreenStatus valueForString(String value) {
        return valueOf(value);
    }
}
