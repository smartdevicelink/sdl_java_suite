package com.smartdevicelink.proxy.rpc.enums;

public enum SeatMemoryActionType {
	/**
	 * Save current seat positions and settings to seat memory.
	 */
	SAVE,
	/**
	 * Restore / apply the seat memory settings to the current seat.
	 */
	RESTORE,
	/**
	 * No action to be performed.
	 */
	NONE,
	;

	public static SeatMemoryActionType valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
