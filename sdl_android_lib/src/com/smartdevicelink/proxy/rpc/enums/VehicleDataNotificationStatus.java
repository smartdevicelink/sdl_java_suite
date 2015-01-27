package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the status of a vehicle data notification.
 * @since SmartDeviceLink 2.0
 */
public enum VehicleDataNotificationStatus implements JsonName {
	NOT_SUPPORTED,
	NORMAL,
	ACTIVE,
	NOT_USED,
	
	;

    /**
     * Convert String to VehicleDataNotificationStatus
     * @param value String
     * @return VehicleDataNotificationStatus
     */    
    public static VehicleDataNotificationStatus valueForString(String value) {
        return valueOf(value);
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static VehicleDataNotificationStatus valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
