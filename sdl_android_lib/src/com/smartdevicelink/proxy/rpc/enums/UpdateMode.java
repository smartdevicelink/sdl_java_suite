package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Specifies what function should be performed on the media clock/counter
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum UpdateMode implements JsonName{
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
    CLEAR,
    
    ;

	
	/**
	 * Returns an UpdateMode value (COUNTUP, COUNTDOWN, PAUSE or RESUME)
	 * @param value a String
	 * @return UpdateMode -COUNTUP, COUNTDOWN, PAUSE or RESUME
	 */
    public static UpdateMode valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static UpdateMode valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }

        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
}
