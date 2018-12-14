package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration listing possible file types.
 * @since SmartDeviceLink 2.0
 */
public enum FileType {
	/**
	 * BMP
	 */
    GRAPHIC_BMP,
    /**
     * JPEG
     */
    GRAPHIC_JPEG,
    /**
     * PNG
     */
    GRAPHIC_PNG,
    /**
     * WAVE
     */
    AUDIO_WAVE,
    AUDIO_AAC,
    /**
     * MP3
     */
    AUDIO_MP3,
    BINARY,
    JSON;

    /**
     * Convert String to FileType
     * @param value String
     * @return FileType
     */      
    public static FileType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
