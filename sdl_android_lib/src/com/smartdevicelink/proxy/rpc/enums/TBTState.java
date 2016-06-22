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
	/**
	 * Confirmation from HMI about accepting the route.
	 */

    ROUTE_ACCEPTED,
    /**
     * Information from HMI about the route refusal.
     */

    ROUTE_REFUSED,
    /**
     * Information from HMI about canceling the route.
     */

    ROUTE_CANCELLED,
    /**
     * Request from HMI for Estimated time of arrival.
     */

    ETA_REQUEST,
    /**
     * Request from HMI for the information of the next turn.
     */

    NEXT_TURN_REQUEST,
    /**
     * Request from HMI for the route status.
     */

    ROUTE_STATUS_REQUEST,
    /**
     * Request from HMI for the route summary.
     */

    ROUTE_SUMMARY_REQUEST,
    /**
     * Request from HMI for the information about trip status.
     */

    TRIP_STATUS_REQUEST,
    /**
     * Request from HMI for the timeout for waiting for the route updating.
     */

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
