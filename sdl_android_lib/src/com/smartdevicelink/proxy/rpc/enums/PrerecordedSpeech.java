package com.smartdevicelink.proxy.rpc.enums;

public enum PrerecordedSpeech {
    HELP_JINGLE,
    INITIAL_JINGLE,
    LISTEN_JINGLE,
    POSITIVE_JINGLE,
    NEGATIVE_JINGLE;

    public static PrerecordedSpeech valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
