package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum WiperStatus implements JsonName {
	OFF,
	AUTO_OFF,
	OFF_MOVING,
	MAN_INT_OFF,
	MAN_INT_ON,
	MAN_LOW,
	MAN_HIGH,
	MAN_FLICK,
	WASH,
	AUTO_LOW,
	AUTO_HIGH,
	COURTESYWIPE,
	AUTO_ADJUST,
	STALLED,
	NO_DATA_EXISTS,
	
	;

    public static WiperStatus valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static WiperStatus valueForJsonName(String name, int sdlVersion){
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
