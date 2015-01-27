package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum VehicleDataResultCode implements JsonName{
	SUCCESS,
	DISALLOWED,
	USER_DISALLOWED,
	INVALID_ID,
	VEHICLE_DATA_NOT_AVAILABLE,
	DATA_ALREADY_SUBSCRIBED,
	DATA_NOT_SUBSCRIBED,
	IGNORED,
	
	;

    public static VehicleDataResultCode valueForString(String value) {
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
    public static VehicleDataResultCode valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
