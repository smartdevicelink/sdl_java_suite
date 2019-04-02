/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
	 * <br><b>THIS ENUM VALUE WILL CHANGE IN FUNCITONALITY DURING THE NEXT MAJOR RELEASE!</b>
	 * <br><br>
	 * This ButtonName value originally was used for both the OK button and PLAY_PAUSE button. As of
	 * SmartDeviceLink 5.0.0, the functionality was broken out into the OK and PLAY_PAUSE buttons.
	 * <br><br> For this version of the library OK will be received for both OK and PLAY_PAUSE to
	 * mitigate a potential break in functionliaty. If the desire is only for the OK functionality,
	 * this button should still be used. If the desired functionality was actually for the play/pause
	 * toggle, then the new PLAY_PAUSE should be used.
	 * <br><br>
	 * Represents the button usually labeled "OK". A typical use of this button
	 * is for the user to press it to make a selection (and until a major library version release,
	 * play pause toggle).
	 *
	 * @since SmartDeviceLink 1.0
	 * @see #PLAY_PAUSE
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
	PRESET_9,
	CUSTOM_BUTTON,
	SEARCH,
	AC_MAX,
	AC,
	RECIRCULATE,
	FAN_UP,
	FAN_DOWN,
	TEMP_UP,
	TEMP_DOWN,
	DEFROST_MAX,
	DEFROST,
	DEFROST_REAR,
	UPPER_VENT,
	LOWER_VENT,
	VOLUME_UP,
	VOLUME_DOWN,
	EJECT,
	SOURCE,
	SHUFFLE,
	REPEAT,
	/**
	 * Represents the play/pause button. A typical use of this button
	 * is for the user to press it to toggle between media playing and pausing.
	 *
	 * <br><br><b>NOTE:</b> This functionality used to be represented by the OK button.
	 *
	 * @since SmartDeviceLink 5.0
	 * @see #OK
	 */
	PLAY_PAUSE,

	;

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
     * @param buttonName the buttonName of PRESET_0 through PRESET_9 to
     * @return Integer that represents which preset the supplied button name represents. It will return null if the
	 *         ButtonName is not one of the PRESET_# names.
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
