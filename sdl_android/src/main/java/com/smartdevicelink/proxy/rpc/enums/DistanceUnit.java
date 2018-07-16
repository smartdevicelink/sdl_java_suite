package com.smartdevicelink.proxy.rpc.enums;

public enum DistanceUnit {
	MILES,
	KILOMETERS,
	;

	public static DistanceUnit valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
