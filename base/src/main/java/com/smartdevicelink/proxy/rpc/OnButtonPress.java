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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;

import java.util.Hashtable;

/**
 * <p>
 * Notifies application of button press events for buttons to which the
 * application is subscribed. SDL supports two button press events defined as
 * follows:
 * </p>
 * <ul>
 * <li>SHORT - Occurs when a button is depressed, then released within two
 * seconds. The event is considered to occur immediately after the button is
 * released.</li>
 * <li>LONG - Occurs when a button is depressed and held for two seconds or
 * more. The event is considered to occur immediately after the two second
 * threshold has been crossed, before the button is released</li>
 * </ul>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>The application will receive OnButtonPress notifications for all
 * subscribed buttons when HMILevel is FULL.</li>
 * <li>The application will receive OnButtonPress notifications for subscribed
 * media buttons when HMILevel is LIMITED.</li>
 * <li>Media buttons include SEEKLEFT, SEEKRIGHT, TUNEUP, TUNEDOWN, and
 * PRESET_0-PRESET_9.</li>
 * <li>The application will not receive OnButtonPress notification when HMILevel
 * is BACKGROUND or NONE.</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li> Any </li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>MAIN, VR. In MENU, only PRESET buttons. In VR, pressing any subscribable
 * button will cancel VR.</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>buttonName</td>
 * <td>{@linkplain ButtonName}</td>
 * <td>Name of the button which triggered this event</td>
 * <td></td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>buttonPressMode</td>
 * <td>{@linkplain ButtonPressMode}</td>
 * <td>Indicates whether this is an SHORT or LONG button press event.</td>
 * <td></td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>customButtonID</td>
 * <td>Integer</td>
 * <td>If ButtonName is "CUSTOM_BUTTON", this references the integer ID passed
 * by a custom button. (e.g. softButton ID)</td>
 * <td>N</td>
 * <td>Minvalue=0 Maxvalue=65536</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see SubscribeButton
 * @see UnsubscribeButton
 */
public class OnButtonPress extends RPCNotification {
	public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";
	public static final String KEY_BUTTON_NAME = "buttonName";
	public static final String KEY_CUSTOM_BUTTON_ID = "customButtonID";
	/**
	*Constructs a newly allocated OnButtonPress object
	*/   
    public OnButtonPress() {
        super(FunctionID.ON_BUTTON_PRESS.toString());
    }
    /**
	 * <p>
	 * Constructs a newly allocated OnButtonPress object indicated by the
	 * Hashtable parameter
	 * </p>
	 * 
	 * @param hash
	 *            The Hashtable to use
     */    
    public OnButtonPress(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     *Constructs a newly allocated OnButtonPress object
     * @param buttonName name of the button
     * @param buttonPressMode indicates whether this is a short or long press
     */
    public OnButtonPress(@NonNull ButtonName buttonName, @NonNull ButtonPressMode buttonPressMode) {
        this();
        setButtonName(buttonName);
        setButtonPressMode(buttonPressMode);
    }
    /**
     * <p>Returns an <i>{@linkplain ButtonName}</i> the button's name</p>
     * @return ButtonName Name of the button
     */    
    public ButtonName getButtonName() {
        return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }
    /**
     * <p>Set the button's name</p>    
     * @param buttonName name of the button
     */    
    public void setButtonName( @NonNull ButtonName buttonName ) {
        setParameters(KEY_BUTTON_NAME, buttonName);
    }
    /**<p>Returns <i>{@linkplain ButtonPressMode}</i></p>
     * @return ButtonPressMode whether this is a long or short button press event
     */    
    public ButtonPressMode getButtonPressMode() {
        return (ButtonPressMode) getObject(ButtonPressMode.class, KEY_BUTTON_PRESS_MODE);
    }
    /**
     * <p>Set the button press mode of the event</p>
     * @param buttonPressMode indicates whether this is a short or long press
     */    
    public void setButtonPressMode( @NonNull ButtonPressMode buttonPressMode ) {
        setParameters(KEY_BUTTON_PRESS_MODE, buttonPressMode);
    }

    @Deprecated
    /**
    * @deprecated use {@link #setCustomButtonID(Integer)} ()} instead.
    */
    public void setCustomButtonName(Integer customButtonID) {
        setParameters(KEY_CUSTOM_BUTTON_ID, customButtonID);
    }
    @Deprecated
    /**
     * @deprecated use {@link #getCustomButtonID()} ()} instead.
    */
    public Integer getCustomButtonName() {
    	return getInteger(KEY_CUSTOM_BUTTON_ID);
    }

    /**
     * Set CustomButtonID of the button
     * If ButtonName is "CUSTOM_BUTTON", this references the integer ID passed by a custom button. (e.g. softButton ID)
     * @param customButtonID CustomButtonID of the button
     */
    public void setCustomButtonID(Integer customButtonID) {
        setParameters(KEY_CUSTOM_BUTTON_ID, customButtonID);
    }

    /**
     * Get CustomButtonID of the button
     * If ButtonName is "CUSTOM_BUTTON", this references the integer ID passed by a custom button. (e.g. softButton ID)
     * @return CustomButtonID of the button
     */
    public Integer getCustomButtonID() {
        return getInteger(KEY_CUSTOM_BUTTON_ID);
    }
}
