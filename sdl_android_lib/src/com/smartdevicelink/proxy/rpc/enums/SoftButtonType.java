package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;
/** The enumeration defines the types of the soft buttons to be displayed on UI component:<br>
1)	 The text is displayed on the soft button<br>
2)	 The image is displayed  on the soft button<br
3)	 Both image and text are displayed on the soft button.<br>
* <p>
* @since SmartDeviceLink 2.0
*/
public enum SoftButtonType {
	/** Text displayed
	 * 
	 */
	SBT_TEXT("TEXT"),
	/** Image displayed
	 * 
	 */
	SBT_IMAGE("IMAGE"),
	/** Image displayed
	 * 
	 */
	SBT_BOTH("BOTH");

	private final String INTERNAL_NAME;
    
    private SoftButtonType(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    public static SoftButtonType valueForString(String value) {
        if(value == null){
            return null;
        }
        
    	for (SoftButtonType anEnum : EnumSet.allOf(SoftButtonType.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
