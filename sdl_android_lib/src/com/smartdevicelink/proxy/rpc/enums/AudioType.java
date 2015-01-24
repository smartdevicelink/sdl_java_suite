package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Describes different audio type options for PerformAudioPassThru
 * 
 */
public enum AudioType implements JsonName {
	/**
	 * PCM raw audio
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    PCM,
    
    ;

    public static AudioType valueForString(String value) {
        return valueOf(value);
    }
    
    public static AudioType valueForJsonName(String name, int sdlVersion){
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
