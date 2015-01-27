package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Reflects the current primary audio source of SDL (if selected).
 * @since SmartDeviceLink 2.0
 */
public enum PrimaryAudioSource implements JsonName{
	/**
	 * Currently no source selected
	 */
    NO_SOURCE_SELECTED,
    /**
     * USB is current source
     */
    USB,
    /**
     * USB2 is current source
     */
    USB2,
    /**
     * Bluetooth Stereo is current source
     */
    BLUETOOTH_STEREO_BTST,
    /**
     * Line in is current source
     */
    LINE_IN,
    /**
     * iPod is current source
     */
    IPOD,
    /**
     * Mobile app is current source
     */
    MOBILE_APP,
    
    ;

    /**
     * Convert String to PrimaryAudioSource
     * @param value String
     * @return PrimaryAudioSource
     */	
    public static PrimaryAudioSource valueForString(String value) {
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
    public static PrimaryAudioSource valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }
}
