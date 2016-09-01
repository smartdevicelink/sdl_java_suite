package com.smartdevicelink.proxy.rpc.enums;
/**
 * For touchscreen interactions, the mode of how the choices are presented.
 * 
 *
 */
public enum LayoutMode {
	/**
	 *  	This mode causes the interaction to display the previous set of choices as icons.
	 */
    ICON_ONLY,
    /**
     * This mode causes the interaction to display the previous set of choices as icons along with a search field in the HMI.
     */
    ICON_WITH_SEARCH,
    /**
     * This mode causes the interaction to display the previous set of choices as a list
     */
    LIST_ONLY,
    /**
     * This mode causes the interaction to display the previous set of choices as a list along with a search field in the HMI.
     */
    LIST_WITH_SEARCH,
    /**
     * 	This mode causes the interaction to immediately display a keyboard entry through the HMI.
     */
    KEYBOARD;

    public static LayoutMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
