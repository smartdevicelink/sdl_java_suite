package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Describes different bit depth options for PerformAudioPassThru.
 *  @see PerformAudioPassThru
 *  @since SmartDeviceLink 2.0
 */
public enum BitsPerSample {
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
	_16_BIT("16_BIT");

    private final String INTERNAL_NAME;
    
    private BitsPerSample(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    public static BitsPerSample valueForString(String value) {
        if(value == null){
            return null;
        }
        
    	for (BitsPerSample anEnum : EnumSet.allOf(BitsPerSample.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
