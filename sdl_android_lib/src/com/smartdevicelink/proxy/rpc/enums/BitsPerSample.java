package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Describes different bit depth options for PerformAudioPassThru
 * 
 */
public enum BitsPerSample implements JsonName{
	/**
	 * 8 bits per sample
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_8_BIT("8_BIT"),
	/**
	 * 16 bits per sample
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_16_BIT("16_BIT"),
	
	;

    String internalName;
    
    private BitsPerSample(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    public static BitsPerSample valueForString(String value) {       	
    	for (BitsPerSample anEnum : EnumSet.allOf(BitsPerSample.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static BitsPerSample valueForJsonName(String name, int sdlVersion){
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
            return this.internalName;
        }
    }
}
