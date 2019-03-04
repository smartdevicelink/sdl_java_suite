package com.smartdevicelink.proxy.rpc.enums;

public enum ElectronicParkBrakeStatus {
	/**
	 * Park brake actuators have been fully applied.
	 */
	CLOSED,
	/**
	 * Park brake actuators are transitioning to either Apply/Closed or Release/Open state.
	 */
	TRANSITION,
	/**
	 *  Park brake actuators are released.
	 */
	OPEN,
	/**
	 * When driver pulls the Electronic Park Brake switch while driving "at speed".
	 */
	DRIVE_ACTIVE,
	/**
	 * When system has a fault or is under maintenance.
	 */
	FAULT,
	;

	/**
	 * Convert String to ElectronicParkBrakeStatus
	 * @param value String
	 * @return ElectronicParkBrakeStatus
	 */
	public static ElectronicParkBrakeStatus valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
