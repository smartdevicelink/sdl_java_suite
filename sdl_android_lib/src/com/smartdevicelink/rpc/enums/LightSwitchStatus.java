package com.smartdevicelink.rpc.enums;

public enum LightSwitchStatus {
	OFF,
	PARKLAMP,
	HEADLAMP,
	AUTOLAMP;

    public static LightSwitchStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
