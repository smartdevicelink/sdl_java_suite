package com.smartdevicelink.proxy.rpc.enums;
/**
 * Contains information about TTS capabilities on the SDL platform.
 * 
 * @since SmartDeviceLink 1.0
 */
public enum SpeechCapabilities {
	/**
	 * The SDL platform can speak text phrases.
	 * 
	 * @since SmartDeviceLink 1.0
	 */	
    TEXT,
    /**
     * The SDL platform can interpret and speak LHPLUS phonemes
     */

    SAPI_PHONEMES,
    
    /**
     * The SDL platform can interpret and speak LHPLUS phonemes
     */

    LHPLUS_PHONEMES,
    /**
     * The SDL platform can play pre-recorded sounds as part of a TTS operation.<p>(e.g. Speak, Alert, PerformInteraction, etc.).</p> 
     */
    PRE_RECORDED,
    /**
     * The SDL platform can play the prerecorded sound of 1 second of silence (i.e. no sound at all).
     */

    SILENCE;
    /**
     * Convert String to SpeechCapabilities
      * @param value String
      * @return SpeechCapabilities
      */

    public static SpeechCapabilities valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
