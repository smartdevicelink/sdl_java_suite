package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration that describes the status of the turn light indicator.
 *
 * @since SmartDeviceLink 5.0
 */
public enum TurnSignal {

	/**
	 * Turn signal is OFF
	 */
	OFF,
	/**
	 * Left turn signal is on
	 */
	LEFT,
	/**
	 * Right turn signal is on
	 */
	RIGHT,
	/**
	 * Both signals (left and right) are on.
	 */
	BOTH,
	;

	public static TurnSignal valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
