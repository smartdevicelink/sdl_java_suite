package com.smartdevicelink.proxy.rpc.enums;
/** This enumeration reflects the status of the wipers.
 * 
 * @since SmartDeviceLink 2.0
 * 
 * @see GetVehicleData
 * @see OnVehicleData
 */

public enum WiperStatus {
	/** The wipers are off.
	 * 
	 */

	OFF,
	/** The wipers are automatically off after detecting the wipers do not need to be engaged (rain stopped, etc.).
	 * 
	 */

	AUTO_OFF,
	/** Means that though set to off, somehow the wipers have been engaged (physically moved enough to engage a wiping motion).
	 * 
	 */

	OFF_MOVING,
	/** The wipers are manually off after having been working.
	 * 
	 */

	MAN_INT_OFF,
	/** The wipers are manually on.
	 * 
	 */

	MAN_INT_ON,
	/** The wipers are manually set to low speed.
	 * 
	 */

	MAN_LOW,
	/** The wipers are manually set to high speed.
	 * 
	 */

	MAN_HIGH,
	/** The wipers are manually set for doing a flick.
	 * 
	 */

	MAN_FLICK,
	/** The wipers are set to use the water from vehicle washer bottle for cleaning the windscreen.
	 * 
	 */

	WASH,
	/** The wipers are automatically set to low speed.
	 * 
	 */

	AUTO_LOW,
	/** The wipers are automatically set to high speed.
	 * 
	 */

	AUTO_HIGH,
	/** This is for when a user has just initiated a WASH and several seconds later a secondary wipe is automatically initiated to clear remaining fluid
	 */

	COURTESYWIPE,
	/** This is set as the user moves between possible automatic wiper speeds.
	 * 
	 */

	AUTO_ADJUST,
	/** The wiper is stalled to its place. There may be an obstruction.
	 * 
	 */

	STALLED,
	/** The sensor / module cannot provide any information for wiper.
	 * 
	 */

	NO_DATA_EXISTS;
	/**
	 * Convert String to WiperStatus
	 * @param value String
	 * @return WiperStatus
	 */

    public static WiperStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
