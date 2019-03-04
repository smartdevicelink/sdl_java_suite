package com.smartdevicelink.proxy.rpc.enums;

public enum NavigationAction {

	/**
	 * Using this action plus a supplied direction can give the type of turn.
	 */
	TURN,

	EXIT,

	STAY,

	MERGE,

	FERRY,

	CAR_SHUTTLE_TRAIN,

	WAYPOINT,

	;

	public static NavigationAction valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
