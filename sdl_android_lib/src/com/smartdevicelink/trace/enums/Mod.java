package com.smartdevicelink.trace.enums;

public enum Mod {
	  TRANSPORT,
	  PROTOCOL,
	  MARSHALL,
	  RPC,
	  APP,
	  PROXY;

	public static Mod valueForString(String value) {
		try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
	}
};