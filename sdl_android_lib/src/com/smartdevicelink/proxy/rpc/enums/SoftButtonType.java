package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum SoftButtonType implements JsonName{
	SBT_TEXT("TEXT"),
	SBT_IMAGE("IMAGE"),
	SBT_BOTH("BOTH"),
	
	;

    String internalName;
    
    private SoftButtonType(String internalName) {
        this.internalName = internalName;
    }
    
    public String toString() {
        return this.internalName;
    }
    
    public static SoftButtonType valueForString(String value) {       	
    	for (SoftButtonType anEnum : EnumSet.allOf(SoftButtonType.class)) {
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
    public static SoftButtonType valueForJsonName(String name, int sdlVersion){
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
