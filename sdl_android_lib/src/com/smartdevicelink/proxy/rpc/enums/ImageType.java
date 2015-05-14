package com.smartdevicelink.proxy.rpc.enums;

/**
 * Contains information about the type of image.
 * @since SmartDeviceLink 2.0 
 */
public enum ImageType {
	/**
	 * Just the static hex icon value to be used
	 */
    STATIC,
    /**
     * Binary image file to be used (identifier to be sent by PutFile)
     */
    DYNAMIC;

    /**
     * Convert String to ImageType
     * @param value String
     * @return ImageType
     */
    public static ImageType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
