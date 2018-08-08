package com.smartdevicelink.proxy.rpc.enums;

public enum ModuleType {
	CLIMATE,
	RADIO,
	SEAT,
	;

	public static ModuleType valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
