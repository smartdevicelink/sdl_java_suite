package com.smartdevicelink.proxy.rpc.enums;

public enum SdlDisconnectedReason {
	
	/** 
	 * User ends the application. 
	 * 
	 * @since SmartDeviceLink 1.0 
	 */
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
    
    TRANSPORT_ERROR,
    APPLICATION_REQUESTED_DISCONNECT,
    DEFAULT,
    TRANSPORT_DISCONNECT,
    HB_TIMEOUT,
    BLUETOOTH_DISABLED,
    BLUETOOTH_ADAPTER_ERROR,
    SDL_REGISTRATION_ERROR,
    APP_INTERFACE_UNREG,
    GENERIC_ERROR,
	
    /**
	 * The app is not being authorized to be connected to SDL.
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	APP_UNAUTHORIZED,
	
	/**
	 * The app has committed a protocol violation.
	 * 
	 * @since SmartDeviceLink 4.0
	 */
	PROTOCOL_VIOLATION;
	
	public static SdlDisconnectedReason valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
	
	public static boolean isUnregisteredReason(SdlDisconnectedReason reason) {
		if (reason == APP_UNAUTHORIZED || reason == PROTOCOL_VIOLATION) {
			return true;
		}
		
		return false;
	}
	
	@Deprecated
	public static SdlDisconnectedReason convertAppInterfaceUnregisteredReason(AppInterfaceUnregisteredReason reason) {
	    if(reason == null){
	        return null;
	    }
		
		SdlDisconnectedReason returnReason = SdlDisconnectedReason.DEFAULT;
		
		switch(reason) {
			case USER_EXIT:
				returnReason = SdlDisconnectedReason.USER_EXIT;
				break;
			case IGNITION_OFF:
				returnReason = SdlDisconnectedReason.IGNITION_OFF;
				break;
			case BLUETOOTH_OFF:
				returnReason = SdlDisconnectedReason.BLUETOOTH_OFF;
				break;
			case USB_DISCONNECTED:
				returnReason = SdlDisconnectedReason.USB_DISCONNECTED;
				break;
			case REQUEST_WHILE_IN_NONE_HMI_LEVEL:
				returnReason = SdlDisconnectedReason.REQUEST_WHILE_IN_NONE_HMI_LEVEL;
				break;
			case TOO_MANY_REQUESTS:
				returnReason = SdlDisconnectedReason.TOO_MANY_REQUESTS;
				break;
			case DRIVER_DISTRACTION_VIOLATION:
				returnReason = SdlDisconnectedReason.DRIVER_DISTRACTION_VIOLATION;
				break;
			case LANGUAGE_CHANGE:
				returnReason = SdlDisconnectedReason.LANGUAGE_CHANGE;
				break;
			case MASTER_RESET:
				returnReason = SdlDisconnectedReason.MASTER_RESET;
				break;
			case FACTORY_DEFAULTS:
				returnReason = SdlDisconnectedReason.FACTORY_DEFAULTS;
				break;
			case APP_UNAUTHORIZED:
				returnReason = SdlDisconnectedReason.APP_UNAUTHORIZED;
				break;
			case PROTOCOL_VIOLATION:
				returnReason = SdlDisconnectedReason.PROTOCOL_VIOLATION;
				break;
            default:
                break;
		}
		
		return returnReason;
	}
}