package com.smartdevicelink.proxy.rpc.enums;

public enum RequestType {

	HTTP,
	FILE_RESUME,
	AUTH_REQUEST,
	AUTH_CHALLENGE,
	AUTH_ACK,
	PROPRIETARY;
     
    public static RequestType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
