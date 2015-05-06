package com.smartdevicelink.transport;

/**
 * Defines available types of the transports.
 */
public enum TransportType {
	
	/**
	 * Transport type is Bluetooth.
	 */
	BLUETOOTH,
	
	/**
	 * Transport type is TCP.
	 */
	TCP,
	USB;
	
	public static TransportType valueForString(String value) {
		try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
	}
}
