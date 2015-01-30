package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Contains information about TTS capabilities on the SDL platform.
 * 
 * @since SmartDeviceLink 1.0
 */
public enum SpeechCapabilities implements JsonName{
	/**
	 * The SDL platform can speak text phrases.
	 * 
	 * @since SmartDeviceLink 1.0
	 */	
    TEXT,
    SAPI_PHONEMES,
    LHPLUS_PHONEMES,
    PRE_RECORDED,
    SILENCE,
    
    ;

    public static SpeechCapabilities valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static SpeechCapabilities valueForJsonName(String name, int sdlVersion){
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
