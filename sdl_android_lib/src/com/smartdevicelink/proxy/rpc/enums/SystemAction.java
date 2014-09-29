package com.smartdevicelink.proxy.rpc.enums;

public enum SystemAction {
	DEFAULT_ACTION,
	STEAL_FOCUS,
	KEEP_CONTEXT;

    public static SystemAction valueForString(String value) {
        return valueOf(value);
    }
}
