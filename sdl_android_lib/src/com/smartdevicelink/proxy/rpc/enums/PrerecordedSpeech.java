package com.smartdevicelink.proxy.rpc.enums;
/**
 * Contains a list of Pre-recorded speech items present on the platform.
 * 
 *
 */
public enum PrerecordedSpeech {
    HELP_JINGLE,
    INITIAL_JINGLE,
    LISTEN_JINGLE,
    POSITIVE_JINGLE,
    NEGATIVE_JINGLE;
    /**
     * Convert String to PrerecordedSpeech
     * @param value String
     * @return PrerecordedSpeech
     */

    public static PrerecordedSpeech valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
