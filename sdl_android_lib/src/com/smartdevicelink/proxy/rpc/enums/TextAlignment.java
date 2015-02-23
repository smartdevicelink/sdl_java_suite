package com.smartdevicelink.proxy.rpc.enums;

/**
 * The list of possible alignments of text in a field.
 * @since SmartDeviceLink 1.0
 */
public enum TextAlignment {
	/**
	 * Text aligned left.
	 */
    LEFT_ALIGNED,
    /**
     * Text aligned right.
     */
    RIGHT_ALIGNED,
    /**
     * Text aligned centered.
     */
    CENTERED;

    /**
     * Convert String to TextAlignment
     * @param value String
     * @return TextAlignment
     */
    public static TextAlignment valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
