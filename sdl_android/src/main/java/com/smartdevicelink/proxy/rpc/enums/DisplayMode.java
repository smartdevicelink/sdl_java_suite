package com.smartdevicelink.proxy.rpc.enums;

public enum DisplayMode {
	DAY,
	NIGHT,
	AUTO,
	;

	public static DisplayMode valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
