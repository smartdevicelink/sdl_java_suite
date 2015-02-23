package com.smartdevicelink.proxy.rpc.enums;

/**
 * <p>
 * Defines logical buttons which, on a given SDL unit, would correspond to
 * either physical or soft (touchscreen) buttons. These logical buttons present
 * a standard functional abstraction which the developer can rely upon,
 * independent of the SDL unit. For example, the developer can rely upon the OK
 * button having the same meaning to the user across SDL platforms.
 * </p>
 * <p>
 * The preset buttons (0-9) can typically be interpreted by the application as
 * corresponding to some user-configured choices, though the application is free
 * to interpret these button presses as it sees fit.
 * </p>
 * <p>
 * The application can discover which buttons a given SDL unit implements by
 * interrogating the ButtonCapabilities parameter of the
 * RegisterAppInterface response.
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum ButtonName{
	/**
	 * Represents the button usually labeled "OK". A typical use of this button
	 * is for the user to press it to make a selection.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	OK,
	/**
	 * Represents the seek-left button. A typical use of this button is for the
	 * user to scroll to the left through menu choices one menu item per press.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	SEEKLEFT,
	/**
	 * Represents the seek-right button. A typical use of this button is for the
	 * user to scroll to the right through menu choices one menu item per press.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	SEEKRIGHT,
	/**
	 * Represents a turn of the tuner knob in the clockwise direction one tick.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	TUNEUP,
	/**
	 * Represents a turn of the tuner knob in the counter-clockwise direction
	 * one tick.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	TUNEDOWN,
	/**
	 * Represents the preset 0 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_0,
	/**
	 * Represents the preset 1 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_1,
	/**
	 * Represents the preset 2 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_2,
	/**
	 * Represents the preset 3 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_3,
	/**
	 * Represents the preset 4 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_4,
	/**
	 * Represents the preset 5 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_5,
	/**
	 * Represents the preset 6 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_6,
	/**
	 * Represents the preset 7 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_7,
	/**
	 * Represents the preset 8 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_8,
	/**
	 * Represents the preset 9 button.
	 * 
	 * @since SmartDeviceLink 1.0
	 */
	PRESET_9, CUSTOM_BUTTON, SEARCH;

    public static ButtonName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * indexForPresetButton returns the integer index for preset buttons
     * which match the preset order. E.G.: indexForPresetButton(PRESET_1)
     * returns the value 1. If the buttonName given is not a preset button,
     * the method will return null.
     *  
     * @param buttonName
     * @return Integer
     */
    public static Integer indexForPresetButton(ButtonName buttonName) {
        if(buttonName == null){
            return null;
        }
        
    	Integer returnIndex = null;
    	
    	switch(buttonName) {        	
        	case PRESET_0:
        		returnIndex = 0;
        		break;
        	case PRESET_1:
        		returnIndex = 1;
        		break;
        	case PRESET_2:
        		returnIndex = 2;
        		break;
        	case PRESET_3:
        		returnIndex = 3;
        		break;
        	case PRESET_4:
        		returnIndex = 4;
        		break;
        	case PRESET_5:
        		returnIndex = 5;
        		break;
        	case PRESET_6:
        		returnIndex = 6;
        		break;
        	case PRESET_7:
        		returnIndex = 7;
        		break;
        	case PRESET_8:
        		returnIndex = 8;
        		break;
        	case PRESET_9:
        		returnIndex = 9;
        		break;
            default:
                break;
    	}
    	
    	return returnIndex;
    }
}
