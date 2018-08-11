package com.smartdevicelink.proxy.rpc.enums;

public enum LightStatus {
	ON,
	OFF,
	RAMP_UP,
	RAMP_DOWN,
	UNKNOWN,
	INVALID,
	;

	public static LightStatus valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
