package com.smartdevicelink.proxy.rpc.enums;
/**
 * Status of the light switch.
 * 
 *
 */

public enum LightSwitchStatus {
	OFF,
	PARKLAMP,
	HEADLAMP,
	AUTOLAMP;

    public static LightSwitchStatus valueForString(String value) {
        return valueOf(value);
    }
}
