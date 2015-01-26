package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum PowerModeStatus implements JsonName{
    KEY_OUT,
    KEY_RECENTLY_OUT,
    KEY_APPROVED_0,
    POST_ACCESORY_0,
    ACCESORY_1,
    POST_IGNITION_1,
    IGNITION_ON_2,
    RUNNING_2,
    CRANK_3,
    
    ;

    public static PowerModeStatus valueForString(String value) {
        return valueOf(value);
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.name();
        }
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static PowerModeStatus valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
