package com.smartdevicelink.proxy.rpc.enums;

/**
 * Describes possible states of turn-by-turn module.
 * @since  SmartDeviceLink 1.0
 */
public enum TBTState {
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
    ROUTE_UPDATE_REQUEST_TIMEOUT;

    /**
     * Convert String to TBTState
     * @param value String
     * @return TBTState
     */
    public static TBTState valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
