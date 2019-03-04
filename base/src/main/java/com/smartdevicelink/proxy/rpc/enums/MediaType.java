package com.smartdevicelink.proxy.rpc.enums;


public enum MediaType {

	MUSIC,

	PODCAST,

	AUDIOBOOK,

	OTHER,

	;

	public static MediaType valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
