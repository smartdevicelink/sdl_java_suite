package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Indicates whether choice/command was selected via VR or via a menu selection
 * (using SEEKRIGHT/SEEKLEFT, TUNEUP, TUNEDOWN and OK buttons)
 * 
 * @since SmartDeviceLink 1.0
 * 
 */
public enum TriggerSource {
	/**
	 * Selection made via menu (i.e. using SEEKRIGHT/SEEKLEFT, TUNEUP, TUNEDOWN
	 * and OK buttons)
	 */
	TS_MENU("MENU"),
	/**
	 * Selection made via VR session
	 */
	TS_VR("VR"),
	
	TS_KEYBOARD("KEYBOARD");

    String internalName;
    
    private TriggerSource(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    public static TriggerSource valueForString(String value) {
        for (TriggerSource anEnum : EnumSet.allOf(TriggerSource.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
