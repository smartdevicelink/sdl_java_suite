package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Describes possible states of turn-by-turn module.
 * @since  SmartDeviceLink 1.0
 */
public enum TBTState implements JsonName{
	/**
	 * Indicates that driver requested a route update.
	 */
	ROUTE_UPDATE_REQUEST,
    ROUTE_ACCEPTED,
    ROUTE_REFUSED,
    ROUTE_CANCELLED,
    ETA_REQUEST,
    NEXT_TURN_REQUEST,
    ROUTE_STATUS_REQUEST,
    ROUTE_SUMMARY_REQUEST,
    TRIP_STATUS_REQUEST,
    ROUTE_UPDATE_REQUEST_TIMEOUT,
    
    ;

    /**
     * Convert String to TBTState
     * @param value String
     * @return TBTState
     */
    public static TBTState valueForString(String value) {
        return valueOf(value);
    }

    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static TBTState valueForJsonName(String name, int sdlVersion){
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
