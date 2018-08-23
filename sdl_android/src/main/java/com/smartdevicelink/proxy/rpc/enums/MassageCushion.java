package com.smartdevicelink.proxy.rpc.enums;

/**
 * List possible cushions of a multi-contour massage seat.
 */
public enum MassageCushion {
	TOP_LUMBAR,
	MIDDLE_LUMBAR,
	BOTTOM_LUMBAR,
	BACK_BOLSTERS,
	SEAT_BOLSTERS,
	;

	public static MassageCushion valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
