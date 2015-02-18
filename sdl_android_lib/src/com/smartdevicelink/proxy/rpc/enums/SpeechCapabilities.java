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
    SAPI_PHONEMES,
    LHPLUS_PHONEMES,
    PRE_RECORDED,
    SILENCE;

    public static SpeechCapabilities valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
