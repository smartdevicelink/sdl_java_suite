package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the status of a vehicle data event; e.g. a seat belt event status.
 * @since SmartDeviceLink 2.0
 */
public enum VehicleDataEventStatus implements JsonName{
	/**
	 * No event available
	 */
	NO_EVENT("NO_EVENT"),
	NO("NO"),
	YES("YES"),
	/**
	 * Vehicle data event is not support 
	 */
	NOT_SUPPORTED("NOT_SUPPORTED"),
	FAULT("FAULT"),
	
	;

	private final String jsonName;
	private VehicleDataEventStatus(String jsonName){
	    this.jsonName = jsonName;
	}
	
    /**
     * Convert String to VehicleDataEventStatus
     * @param value String
     * @return VehicleDataEventStatus
     */   	
    public static VehicleDataEventStatus valueForString(String value) {
        return valueOf(value);
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.jsonName;
        }
    }

    public static VehicleDataEventStatus valueForJsonName(String jsonName, int sdlVersion){
        if(jsonName == null) return null;

        for(VehicleDataEventStatus anEnum : EnumSet.allOf(VehicleDataEventStatus.class)){
            if(anEnum.getJsonName(sdlVersion).equals(jsonName)){
                return anEnum;
            }
        }
        return null;
    }
}
