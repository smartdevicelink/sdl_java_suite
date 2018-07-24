package com.smartdevicelink.api.screen;

/**
 * Enumeration listing possible text alignments.
 */
public enum TextAlignment {
	/**
	 * left alignment
	 */
	LEFT,
	/**
	 * right alignment
	 */
	RIGHT,
	/**
	 * center alignment
	 */
	CENTER,
	;

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
