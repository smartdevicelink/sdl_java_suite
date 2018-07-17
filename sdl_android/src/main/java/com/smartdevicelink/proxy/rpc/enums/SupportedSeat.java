package com.smartdevicelink.proxy.rpc.enums;

/**
 * List possible seats that is a remote controllable seat.
 */
public enum SupportedSeat {
	DRIVER,
	FRONT_PASSENGER,
	;

	public static SupportedSeat valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
