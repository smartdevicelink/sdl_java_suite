package com.smartdevicelink.proxy.rpc.enums;
/** The type of touch event.
 * 
 * @since SmartDeviceLink 2.3.2
 *
 */

public enum TouchType {
	/** The user has touched the screen.
	 * 
	 */

    BEGIN,
	 /** The User has moved his finger over the screen.
     * 
     */

    MOVE,
    /** The User has removed his finger from the screen.
     * 
     */

    END;
	   /**
  * Convert String to TouchType
  * @param value String
  * @return TouchType
  */

    public static TouchType valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}