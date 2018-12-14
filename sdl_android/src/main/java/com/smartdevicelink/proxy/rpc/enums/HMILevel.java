package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;

/**
 * Specifies current level of the HMI. An HMI level indicates the degree of user interaction possible through the HMI (e.g. TTS only, display only, VR, etc.). The HMI level varies for an application based on the type of display (i.e. Nav or non-Nav) and the user directing "focus" to other applications (e.g. phone, other mobile applications, etc.)
 * 
 * @since SmartDeviceLink 1.0
 */
public enum HMILevel {
	/**
	 * The application has full use of the SDL HMI. The app may output via TTS, display, or streaming audio and may gather input via VR, Menu, and button presses
	 */
    HMI_FULL("FULL"),
    /**
     * This HMI Level is only defined for a media application using an HMI with an 8 inch touchscreen (Nav) system. The application's {@linkplain com.smartdevicelink.proxy.rpc.Show} text is displayed and it receives button presses from media-oriented buttons (SEEKRIGHT, SEEKLEFT, TUNEUP, TUNEDOWN, PRESET_0-9)
     */
    HMI_LIMITED("LIMITED"),
    /**
     * App cannot interact with user via TTS, VR, Display or Button Presses. App can perform the following operations:
     * <ul>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.AddCommand}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.DeleteCommand}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.AddSubMenu}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.DeleteSubMenu}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.SubscribeButton}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.UnsubscribeButton}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.Show}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.UnregisterAppInterface}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.ResetGlobalProperties}</li>
     * <li>Operation {@linkplain com.smartdevicelink.proxy.rpc.SetGlobalProperties}</li>
     * </ul>
     */
    HMI_BACKGROUND("BACKGROUND"),
    /**
     * Application has been discovered by SDL, but application cannot send any requests or receive any notifications
     * An HMILevel of NONE can also mean that the user has exited the application by saying "exit appname" or selecting "exit" from the application's menu. When this happens, the application still has an active interface registration with SDL and all SDL resources the application has created (e.g. Choice Sets, subscriptions, etc.) still exist. But while the HMILevel is NONE, the application cannot send any messages to SDL, except <i>{@linkplain com.smartdevicelink.proxy.rpc.UnregisterAppInterface}</li>
     */
    HMI_NONE("NONE");

    private final String INTERNAL_NAME;
    
    private HMILevel(String internalName) {
        this.INTERNAL_NAME = internalName;
    }
    
    public String toString() {
        return this.INTERNAL_NAME;
    }
    
    /**
     * Returns a HMILevel Status (FULL, LIMITED, BACKGROUND or NONE)
     * @param value a String
     * @return HMILevel -a String value (FULL, LIMITED, BACKGROUND or NONE)
     */
    public static HMILevel valueForString(String value) {
        if(value == null){
            return null;
        }
        
        for (HMILevel anEnum : EnumSet.allOf(HMILevel.class)) {
            if (anEnum.toString().equals(value)) {
                return anEnum;
            }
        }
        return null;
    }
}
