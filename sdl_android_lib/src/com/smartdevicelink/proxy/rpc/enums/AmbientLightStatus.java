package com.smartdevicelink.proxy.rpc.enums;

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
