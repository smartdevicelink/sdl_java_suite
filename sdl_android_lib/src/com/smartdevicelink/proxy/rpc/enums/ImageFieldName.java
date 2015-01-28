package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

public enum ImageFieldName implements JsonName{
	softButtonImage,
	choiceImage,
	choiceSecondaryImage,
	vrHelpItem,
	turnIcon,
	menuIcon,
	cmdIcon,
	appIcon,
	graphic,
	showConstantTBTIcon,
	showConstantTBTNextTurnIcon,
	
	;
    
	public static ImageFieldName valueForString(String value) {
        return valueOf(value);
    }
    
    /**
     * Returns the enumerated value for a given string and associated SDL version.
     * 
     * @param name The name of the JSON string
     * @param sdlVersion The SDL version associated with the input string
     * @return The enumerated value for the given string or null if it wasn't found
     */
    public static ImageFieldName valueForJsonName(String name, int sdlVersion){
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
            return this.name();
        }
    }
}
