package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration that describes possible states of video streaming.
 * @since SmartDeviceLink 5.0
 */
public enum VideoStreamingState {
	/**
	 * @since SmartDeviceLink 5.0
	 */
	STREAMABLE,
	/**
	 * @since SmartDeviceLink 5.0
	 */
	NOT_STREAMABLE;
    public static VideoStreamingState valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
