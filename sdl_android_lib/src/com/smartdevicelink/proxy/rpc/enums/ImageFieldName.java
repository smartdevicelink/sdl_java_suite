package com.smartdevicelink.proxy.rpc.enums;

public enum ImageFieldName {
	SOFT_BUTTON_IMAGE,
	CHOICE_IMAGE,
	CHOICE_SECONDARY_IMAGE,
	VR_HELP_ITEM,
	TURN_ICON,
	MENU_ICON,
	CMD_ICON,
	APP_ICON,
	GRAPHIC,
	SHOW_CONSTANT_TBT_ICON,
	SHOW_CONSTANT_TBT_NEXT_TURN_ICON,
	/**
     * The optional image of a destination / location
     * 
     * @since SmartDeviceLink 4.0
     */
	LOCATION_IMAGE,
	;
    
	public static ImageFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
