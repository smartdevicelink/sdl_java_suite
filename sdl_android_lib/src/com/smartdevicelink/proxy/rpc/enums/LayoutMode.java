package com.smartdevicelink.proxy.rpc.enums;

public enum LayoutMode {
    ICON_ONLY,
    ICON_WITH_SEARCH,
    LIST_ONLY,
    LIST_WITH_SEARCH,
    KEYBOARD;

    public static LayoutMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
