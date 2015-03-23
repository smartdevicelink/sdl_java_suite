package com.smartdevicelink.proxy.rpc.enums;

public enum LayoutMode {
    ICON_ONLY,
    /**
     * @deprecated As of SmartDeviceLink 4.0
     */
    ICON_WITH_SEARCH,
    /**
	 * @since SmartDeviceLink 4.0
     */
    ICONS_WITH_SEARCH,
    LIST_ONLY,
    /**
     * @deprecated As of SmartDeviceLink 4.0
     */
    LIST_WITH_SEARCH,
    /**
	 * @since SmartDeviceLink 4.0
     */
    LISTS_WITH_SEARCH,
    KEYBOARD;

    public static LayoutMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
