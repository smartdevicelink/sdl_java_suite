package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the ignition switch stability.
 * @since SmartDeviceLink 2.0
 */
public enum IgnitionStableStatus implements JsonName{
	/**
	 * The current ignition switch status is considered not to be stable.
	 */
	IGNITION_SWITCH_NOT_STABLE,
	/**
	 * The current ignition switch status is considered to be stable.
	 */
	IGNITION_SWITCH_STABLE,
	MISSING_FROM_TRANSMITTER,
	
	;

    /**
     * Convert String to IgnitionStableStatus
     * @param value String
     * @return IgnitionStableStatus
     */    	
    public static IgnitionStableStatus valueForString(String value) {
        return valueOf(value);
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
    
    public static IgnitionStableStatus valueForJsonName(String jsonName, int sdlVersion){
        if(jsonName == null){
            return null;
        }

        switch(sdlVersion){
        default:
            return valueForString(jsonName);
        }
    }
}
