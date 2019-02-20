package com.smartdevicelink.proxy.rpc.enums;

public enum Direction {

	LEFT,

	RIGHT,

	;

	public static Direction valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}
