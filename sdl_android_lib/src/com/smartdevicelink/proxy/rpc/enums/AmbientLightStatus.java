package com.smartdevicelink.proxy.rpc.enums;

/**
 * Stores the ambient light status, possible enum values are:
 * 
 * <ul>
 * 	<li>DAY</li>
 *  <li>NIGHT</li>
 *  <li>UNKNOWN</li>
 *  <li>INVALID</li>
 *  <li>TWILIGHT_1</li>
 *  <li>TWILIGHT_2</li>
 *  <li>TWILIGHT_3</li>
 *  <li>TWILIGHT_4</li>
 * </ul>
 */
public enum AmbientLightStatus {
	
	DAY,
	NIGHT,
	UNKNOWN,
	INVALID,
	TWILIGHT_1,
	TWILIGHT_2,
	TWILIGHT_3,
	TWILIGHT_4;

	/**
	 * Possible AmbientLightStatus return values:
	 * 
	 * <ul>
	 * 	<li>DAY</li>
	 *  <li>NIGHT</li>
	 *  <li>UNKNOWN</li>
	 *  <li>INVALID</li>
	 *  <li>TWILIGHT_1</li>
	 *  <li>TWILIGHT_2</li>
	 *  <li>TWILIGHT_3</li>
	 *  <li>TWILIGHT_4</li>
	 * </ul>
	 * 
	 * @param value A String
	 * @return AmbientLightStatus
	 */
    public static AmbientLightStatus valueForString (String value) {
        try{
            return valueOf(value); 
        } catch(Exception e){ 
            return null;
        }
    }
}