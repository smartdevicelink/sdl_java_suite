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
	PROPRIETARY,
	/** 
     * @since SmartDeviceLink 4.0
     */
	QUERY_APPS,
	/** 
     * @since SmartDeviceLink 4.0
     */
    LAUNCH_APP,
    /** 
     * @since SmartDeviceLink 4.0
     */
    LOCK_SCREEN_ICON_URL,
    /** 
     * @since SmartDeviceLink 4.0
     */
    TRAFFIC_MESSAGE_CHANNEL,
    /** 
     * @since SmartDeviceLink 4.0
     */
    DRIVER_PROFILE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    VOICE_SEARCH, 
    /** 
     * @since SmartDeviceLink 4.0
     */
    NAVIGATION,
    /** 
     * @since SmartDeviceLink 4.0
     */
    PHONE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    CLIMATE,
    /** 
     * @since SmartDeviceLink 4.0
     */
    SETTINGS,
    /** 
     * @since SmartDeviceLink 4.0
     */
    VEHICLE_DIAGNOSTICS,
    /** 
     * @since SmartDeviceLink 4.0
     */
    EMERGENCY,
    /** 
     * @since SmartDeviceLink 4.0
     */
    MEDIA,
    /** 
     * @since SmartDeviceLink 4.0
     */
    FOTA,
	;
	/**
     * Convert String to RequestType
     * @param value String
     * @return RequestType
     */  
    public static RequestType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
