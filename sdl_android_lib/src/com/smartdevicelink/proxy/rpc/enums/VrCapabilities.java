package com.smartdevicelink.proxy.rpc.enums;

import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * The VR capabilities of the connected SDL platform.
 * 
 */
public enum VrCapabilities implements JsonName{
	/**
	 * The SDL platform is capable of recognizing spoken text in the current
	 * language.
	 * 
	 * @since SmartDeviceLink 1.0
	 */   
	Text,
	
	;

    public static VrCapabilities valueForString(String value) {
        if (value.toUpperCase().equals(VrCapabilities.Text.toString().toUpperCase()))
        {
        	return VrCapabilities.Text;
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
    public static VrCapabilities valueForJsonName(String name, int sdlVersion){
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
