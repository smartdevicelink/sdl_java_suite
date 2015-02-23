package com.smartdevicelink.trace.enums;

    
public enum DetailLevel {
	OFF, 
	TERSE, 
	VERBOSE;

    public static DetailLevel valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
