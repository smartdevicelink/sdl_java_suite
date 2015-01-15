package com.smartdevicelink.proxy.rpc.enums;
/**
 * Enumeration listing possible asynchronous requests.
 * 
 *
 */
public enum RequestType {

	HTTP,
	FILE_RESUME,
	AUTH_REQUEST,
	AUTH_CHALLENGE,
	AUTH_ACK,
	PROPRIETARY;
	/**
     * Convert String to RequestType
     * @param value String
     * @return RequestType
     */  
    public static RequestType valueForString(String value) {
        return valueOf(value);
    }
}
