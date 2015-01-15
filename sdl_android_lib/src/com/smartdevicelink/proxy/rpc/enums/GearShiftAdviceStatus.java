package com.smartdevicelink.proxy.rpc.enums;
/**
 * 
 * Reflects the status of the current gear shift advice.
 *
 */
public enum GearShiftAdviceStatus {
	NO_INDICATION,
	UPSHIFT_FUEL_ECONOMY,
	UPSHIFT_PERFORMANCE,
	UPSHIFT_WARNING,
	DOWNSHIFT_RECOMMENDATION,
	SHIFT_TO_NEUTRAL;
	/**
     * Convert String to GearShiftAdviceStatus
     * @param value String
     * @return GearShiftAdviceStatus
     */

    public static GearShiftAdviceStatus valueForString(String value) {
        return valueOf(value);
    }
}
