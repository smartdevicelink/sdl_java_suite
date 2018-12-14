package com.smartdevicelink.trace.enums;

public enum InterfaceActivityDirection {
	Transmit,
	Receive,
	None;
	
	public static InterfaceActivityDirection valueForString(String value) {
		try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
	}
}