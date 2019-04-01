package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;


public enum Jingle{
    POSITIVE("POSITIVE_JINGLE"),
    NEGATIVE("NEGATIVE_JINGLE"),
    INITIAL("INITIAL_JINGLE"),
    LISTEN("LISTEN_JINGLE"),
    HELP("HELP_JINGLE");
    
    private final String INTERNAL_NAME;
    
    private Jingle(String name){
        this.INTERNAL_NAME = name;
    }
    
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    public static Jingle valueForString(String value) {          
        for (Jingle anEnum : EnumSet.allOf(Jingle.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
