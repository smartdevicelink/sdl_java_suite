package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Describes different sampling rates for PerformAudioPassThru
 * 
 */
public enum SamplingRate {

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
	_44KHZ("44KHZ");

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
}
