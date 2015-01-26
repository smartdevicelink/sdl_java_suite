package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Describes different sampling rates for PerformAudioPassThru
 * 
 */
public enum SamplingRate implements JsonName{

	/**
	 * Sampling rate of 8 kHz
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_8KHZ("8KHZ"),
	/**
	 * Sampling rate of 16 kHz
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_16KHZ("16KHZ"),
	/**
	 * Sampling rate of 22 kHz
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_22KHZ("22KHZ"),
	/**
	 * Sampling rate of 44 kHz
	 * 
	 * @since SmartDeviceLink 2.0
	 */
	_44KHZ("44KHZ"),
	
	;

    String internalName;
    
    private SamplingRate(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    public static SamplingRate valueForString(String value) {       	
    	for (SamplingRate anEnum : EnumSet.allOf(SamplingRate.class)) {
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
    public static SamplingRate valueForJsonName(String name, int sdlVersion){
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
            return internalName;
        }
    }
}
