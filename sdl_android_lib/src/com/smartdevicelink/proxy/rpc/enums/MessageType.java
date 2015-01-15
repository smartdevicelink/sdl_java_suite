package com.smartdevicelink.proxy.rpc.enums;
/** Enumeration linking message types with function types in WiPro protocol.<br>Assumes enumeration starts at value 0
 * .
 * @since SmartDeviceLink 2.0
 *
 */
public enum MessageType {
	/**
	 * value="0"
	 */
    request,
    /**
     * value="1"
     */
    response,
    /**
     * value="2"
     */
    
    notification;
    /**
     * Convert String to MessageType
     * @param value String
     * @return MessageType
     */ 
    public static MessageType valueForString(String value) {
        return valueOf(value);
    }
}
