package com.smartdevicelink.proxy.rpc.enums;
/**
 * 
 * Status of the Lock Screen.
 *
 */
public enum LockScreenStatus {
	/**
	 * LockScreen is Required
	 */

	REQUIRED,
	/**
	 * LockScreen is Optional
	 */

	OPTIONAL,
	/**
	 * LockScreen is Not Required
	 */

	OFF;
	
    public static LockScreenStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
