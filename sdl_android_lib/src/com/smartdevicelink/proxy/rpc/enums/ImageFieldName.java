package com.smartdevicelink.proxy.rpc.enums;

public enum ImageFieldName {
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
	showConstantTBTNextTurnIcon;
    
	public static ImageFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
