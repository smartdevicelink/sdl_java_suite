package com.smartdevicelink.proxy.rpc.enums;

public enum LightSwitchStatus {
	OFF,
	PARKLAMP,
	HEADLAMP,
	AUTOLAMP;

    public static LightSwitchStatus valueForString(String value) {
        return valueOf(value);
    }
}
