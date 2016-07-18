package com.smartdevicelink.proxy.rpc.enums;

public enum SdlDisconnectedReason {
	USER_EXIT,
    IGNITION_OFF,
    BLUETOOTH_OFF,
    USB_DISCONNECTED,
    REQUEST_WHILE_IN_NONE_HMI_LEVEL,
    TOO_MANY_REQUESTS,
    DRIVER_DISTRACTION_VIOLATION,
    LANGUAGE_CHANGE,
    MASTER_RESET,
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
     * This only occurs when multiplexing is running and it is found to be on an old gen 1 system.
     */
    LEGACY_BLUETOOTH_MODE_ENABLED,
    RPC_SESSION_ENDED
    ;

	
	public static SdlDisconnectedReason valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
	
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
            default:
                break;
		}
		
		return returnReason;
	}
}
