package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * Indicates whether choice/command was selected via VR or via a menu selection
 * (using SEEKRIGHT/SEEKLEFT, TUNEUP, TUNEDOWN and OK buttons)
 * 
 * @since SmartDeviceLink 1.0
 * 
 */
public enum TriggerSource implements JsonName{
	/**
	 * Selection made via menu (i.e. using SEEKRIGHT/SEEKLEFT, TUNEUP, TUNEDOWN
	 * and OK buttons)
	 */
	TS_MENU("MENU"),
	/**
	 * Selection made via VR session
	 */
	TS_VR("VR"),
	
	TS_KEYBOARD("KEYBOARD"),
	
	;

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
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static TriggerSource valueForJsonName(String name, int sdlVersion){
        if(name == null){
            return null;
        }
        
        switch(sdlVersion){
        default:
            return valueForString(name);
        }
    }

    @Override
    public String getJsonName(int sdlVersion){
        switch(sdlVersion){
        default:
            return this.internalName;
        }
    }
}
