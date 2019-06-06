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
