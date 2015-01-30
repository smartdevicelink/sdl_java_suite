package com.smartdevicelink.proxy.rpc.enums;

public enum RequestType {

	HTTP,
	FILE_RESUME,
	AUTH_REQUEST,
	AUTH_CHALLENGE,
	AUTH_ACK,
	PROPRIETARY,
	LOCK_SCREEN_ICON_URL,
	
	;
     
    public static RequestType valueForString(String value) {
        return valueOf(value);
    }
}
