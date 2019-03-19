package com.smartdevicelink.proxy.rpc.enums;

/**
 * Specifies what function should be performed on the media clock/counter
 * 
 * 
 * @since SmartDeviceLink 1.0
 */
public enum UpdateMode {
	/**
	 * Starts the media clock timer counting upward, in increments of 1 second
	 */
	COUNTUP,
	/**
	 * Starts the media clock timer counting downward, in increments of 1 second
	 */
	COUNTDOWN,
	/**
	 * Pauses the media clock timer
	 */
	PAUSE,
	/**
	 * Resumes the media clock timer. The timer resumes counting in whatever
	 * mode was in effect before pausing (i.e. COUNTUP or COUNTDOWN)
	 */
	RESUME,
    CLEAR;

	
	/**
	 * Returns an UpdateMode value (COUNTUP, COUNTDOWN, PAUSE or RESUME)
	 * @param value a String
	 * @return UpdateMode -COUNTUP, COUNTDOWN, PAUSE or RESUME
	 */
    public static UpdateMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
