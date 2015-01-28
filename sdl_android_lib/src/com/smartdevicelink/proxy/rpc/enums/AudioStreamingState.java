package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Describes whether or not streaming audio is currently audible to the user.
 * Though provided in every OnHMIStatus notification, this information is only
 * relevant for applications that declare themselves as media apps in
 * RegisterAppInterface
 * 
 * @since SmartDeviceLink 1.0
 */
public enum AudioStreamingState implements JsonName{
	/**
	 * Currently streaming audio, if any, is audible to user.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	AUDIBLE,

	/**
	 * Some kind of audio mixing is taking place. Currently streaming audio, if
	 * any, is audible to the user at a lowered volume.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	ATTENUATED,
	/**
	 * Currently streaming audio, if any, is not audible to user. made via VR
	 * session.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	NOT_AUDIBLE,
	
	;

    public static AudioStreamingState valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static AudioStreamingState valueForJsonName(String name, int sdlVersion){
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
