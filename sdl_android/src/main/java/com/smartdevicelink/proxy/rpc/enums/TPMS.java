package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enums for Tire Pressure Monitoring Systems
 *
 * @since SmartDeviceLink 5.0
 */
public enum TPMS {

	/**
	 * If set the status of the tire is not known.
	 */
	UNKNOWN,
	/**
	 * TPMS does not function.
	 */
	SYSTEM_FAULT,
	/**
	 * The sensor of the tire does not function.
	 */
	SENSOR_FAULT,
	/**
	 * TPMS is reporting a low tire pressure for the tire.
	 */
	LOW,
	/**
	 * TPMS is active and the tire pressure is monitored.
	 */
	SYSTEM_ACTIVE,
	/**
	 * TPMS is reporting that the tire must be trained.
	 */
	TRAIN,
	/**
	 * TPMS reports the training for the tire is completed.
	 */
	TRAINING_COMPLETE,
	/**
	 * TPMS reports the tire is not trained.
	 */
	NOT_TRAINED,
	;

	public static TPMS valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
