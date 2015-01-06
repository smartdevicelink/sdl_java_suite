package com.smartdevicelink.proxy.rpc.enums;
/** Reflects the status of the ambient light sensor.
 * 
 * @since SmartDeviceLink 2.3.2
 * <p>
 * @see SoftButtonCapabilities
 * @see ButtonCapabilities
 * @see OnButtonPress
 *
 */

public enum AmbientLightStatus {
	NIGHT,
	TWILIGHT_1,
	TWILIGHT_2,
	TWILIGHT_3,
	TWILIGHT_4,
	DAY,
	UNKNOWN,
	INVALID;

    public static AmbientLightStatus valueForString(String value) {
        return valueOf(value);
    }
}
