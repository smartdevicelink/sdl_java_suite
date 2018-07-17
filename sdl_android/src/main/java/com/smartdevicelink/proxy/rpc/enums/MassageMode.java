package com.smartdevicelink.proxy.rpc.enums;

/**
 * List possible modes of a massage zone.
 */
public enum MassageMode {
	OFF,
	LOW,
	HIGH,
	;

	public static MassageMode valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
