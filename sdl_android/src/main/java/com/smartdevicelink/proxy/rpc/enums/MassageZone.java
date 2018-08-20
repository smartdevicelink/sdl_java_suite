package com.smartdevicelink.proxy.rpc.enums;

/**
 * List possible zones of a multi-contour massage seat.
 */
public enum MassageZone {
	/**
	 * The back of a multi-contour massage seat. or SEAT_BACK
	 */
	LUMBAR,
	/**
	 * The bottom a multi-contour massage seat. or SEAT_BOTTOM
	 */
	SEAT_CUSHION,
	;

	public static MassageZone valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
