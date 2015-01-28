package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Indicates reason why app interface was unregistered. The application is being
 * disconnected by SDL.
 * 
 * @since SmartDeviceLink 1.0
 */
public enum AppInterfaceUnregisteredReason implements JsonName{
	USER_EXIT,
	/**
	 * Vehicle ignition turned off.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	IGNITION_OFF,
	/**
	 * Bluetooth was turned off, causing termination of a necessary Bluetooth
	 * connection.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	BLUETOOTH_OFF,
	/**
	 * USB was disconnected, causing termination of a necessary iAP connection.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	USB_DISCONNECTED,
	/**
	 * Application attempted SmartDeviceLink RPC request while {@linkplain HMILevel}
	 * =NONE. App must have HMILevel other than NONE to issue RPC requests or
	 * get notifications or RPC responses.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	REQUEST_WHILE_IN_NONE_HMI_LEVEL,
	/**
	 * Either too many -- or too many per unit of time -- requests were made by
	 * the application.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	TOO_MANY_REQUESTS,
	/**
	 * The application has issued requests which cause driver distraction rules
	 * to be violated.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	DRIVER_DISTRACTION_VIOLATION,
	/**
	 * The user has changed the language in effect on the SDL platform to a
	 * language that is incompatible with the language declared by the
	 * application in its RegisterAppInterface request.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	LANGUAGE_CHANGE,
	/**
	 * The user performed a MASTER RESET on the SDL platform, causing removal
	 * of a necessary Bluetooth pairing.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	MASTER_RESET,
	/**
	 * The user restored settings to FACTORY DEFAULTS on the SDL platform.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	FACTORY_DEFAULTS, 
	/**
	 * The app is not being authorized to be connected to SDL.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	APP_UNAUTHORIZED,
	
	;

    public static AppInterfaceUnregisteredReason valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static AppInterfaceUnregisteredReason valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
}
