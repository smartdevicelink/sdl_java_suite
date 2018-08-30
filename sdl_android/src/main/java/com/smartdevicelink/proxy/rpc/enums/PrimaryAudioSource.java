package com.smartdevicelink.proxy.rpc.enums;

/**
 * Reflects the current primary audio source of SDL (if selected).
 * @since SmartDeviceLink 2.0
 */
public enum PrimaryAudioSource {
    /**
     * Currently no source selected
     */
    NO_SOURCE_SELECTED,
    /**
     * CD is current source
     */
    CD,
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
    AM,
    FM,
    XM,
    DAB,
    ;

    /**
     * Convert String to PrimaryAudioSource
     * @param value String
     * @return PrimaryAudioSource
     */
    public static PrimaryAudioSource valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
    }
}
