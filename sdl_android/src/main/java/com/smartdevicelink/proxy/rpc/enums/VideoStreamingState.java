package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration that describes possible states of video streaming.
 * @since SmartDeviceLink 4.6
 */
public enum VideoStreamingState {
	/**
	 * @since SmartDeviceLink 4.6
	 */
	STREAMABLE,
	/**
	 * @since SmartDeviceLink 4.6
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
