package com.smartdevicelink.proxy.rpc.enums;

/**
 * The list of potential compass directions
 * @since SmartDeviceLink 2.0 
 */
public enum CompassDirection {
	/**
	 * Direction North
	 */
	NORTH,
	/**
	 * Direction Northwest
	 */
	NORTHWEST,
	/**
	 * Direction West
	 */
	WEST,
	/**
	 * Direction Southwest
	 */
	SOUTHWEST,
	/**
	 * Direction South
	 */
	SOUTH,
	/**
	 * Direction Southeast
	 */
	SOUTHEAST,
	/**
	 * Direction East
	 */
	EAST,
	/**
	 * Direction Northeast
	 */
	NORTHEAST;

    /**
     * Convert String to CompassDirection
     * @param value String
     * @return CompassDirection
     */
    public static CompassDirection valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
