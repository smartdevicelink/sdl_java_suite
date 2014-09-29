package com.smartdevicelink.trace.enums;

    
public enum DetailLevel {
	OFF, 
	TERSE, 
	VERBOSE;

    public static DetailLevel valueForString(String value) {
        return valueOf(value);
    }
}
