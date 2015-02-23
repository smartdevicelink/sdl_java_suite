package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

public enum SoftButtonType {
	SBT_TEXT("TEXT"),
	SBT_IMAGE("IMAGE"),
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
