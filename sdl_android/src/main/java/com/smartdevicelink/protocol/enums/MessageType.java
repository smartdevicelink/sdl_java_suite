package com.smartdevicelink.protocol.enums;


public enum MessageType {
//	START_SESSION,
//	START_SESSION_ACK,
//	START_SESSION_NACK,
//	END_SESSION,
	UNDEFINED,
	BULK,
	RPC;
	
	public static MessageType valueForString (String value) {
        try{
            return valueOf(value);
        } catch(Exception e) {
            return null;
        }
    }
}
