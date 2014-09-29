package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * The supported dimensions of the GPS.
 * @since SmartDeviceLink 2.0
 */
public enum Dimension {
	/**
	 * No GPS at all
	 */
    NO_FIX("NO_FIX"),
    /**
     * Longitude and latitude
     */
    _2D("2D"),
    /**
     * Longitude and latitude and altitude
     */
    _3D("3D");
    
    String internalName;

    private Dimension(String internalName) {
    	this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    /**
     * Convert String to Dimension
     * @param value String
     * @return Dimension
     */    
    public static Dimension valueForString(String value) {
    	for (Dimension anEnum : EnumSet.allOf(Dimension.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
