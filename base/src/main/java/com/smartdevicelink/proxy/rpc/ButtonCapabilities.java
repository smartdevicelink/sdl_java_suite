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

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

import java.util.Hashtable;

/**
 * Provides information about the capabilities of a SDL HMI button.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>name</td>
 * 			<td>ButtonName</td>
 * 			<td>The name of theSDL HMI button.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>shortPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a SHORT press. See {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode} for more information.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>longPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a LONG press. See {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode} for more information.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>upDownAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
 *                  <p> When the button is released, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONUP.</p></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 * 
 * <p>Upon the request HMI must provide the list of the following information:</p>
 * 	<p>The names of all existing/supported hardware buttons.</p>
 * 		<p>The availability of LONG/SHORT press for each existing/supported hardware button correspondingly</p>
 * 		<p>The availability of UP/DOWN events for each existing/supported hardware button correspondingly.</p>
 * 
 * @since SmartDeviceLink 1.0
 * 
 * @see ButtonName
 * @see com.smartdevicelink.proxy.rpc.enums.ButtonEventMode
 * @see com.smartdevicelink.proxy.rpc.enums.ButtonPressMode
 * 
 * 
 *
 * @see OnButtonEvent
 * @see OnButtonPress
 * 
 */
public class ButtonCapabilities extends RPCStruct {
	public static final String KEY_NAME = "name";
	public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
	public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
	public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";
	public static final String KEY_MODULE_INFO = "moduleInfo";
	/**
	 * Constructs a newly allocated ButtonCapabilities object
	 */
    public ButtonCapabilities() { }
    /**
     * Constructs a newly allocated ButtonCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public ButtonCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a newly allocated ButtonCapabilities object
     * @param name the name of button
     * @param shortPressAvailable True if support otherwise False.
     * @param longPressAvailable True if support otherwise False.
     * @param upDownAvailable True if support otherwise False.
     */
    public ButtonCapabilities(@NonNull ButtonName name, @NonNull Boolean shortPressAvailable, @NonNull Boolean longPressAvailable, @NonNull Boolean upDownAvailable) {
        this();
        setName(name);
        setShortPressAvailable(shortPressAvailable);
        setLongPressAvailable(longPressAvailable);
        setUpDownAvailable(upDownAvailable);
    }
    /**
     * Get the name of theSDL HMI button.
     * @return ButtonName the name of the Button
     */    
    public ButtonName getName() {
        return (ButtonName) getObject(ButtonName.class, KEY_NAME);
    }
    /**
     * Set the name of theSDL HMI button.
     * @param name the name of button
     */
    public ButtonCapabilities setName(@NonNull ButtonName name) {
        setValue(KEY_NAME, name);
        return this;
    }
    /**
     * Whether the button supports a SHORT press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @return True if support otherwise False.
     */    
    public Boolean getShortPressAvailable() {
        return getBoolean( KEY_SHORT_PRESS_AVAILABLE );
    }
    /**
     * Set the button supports a SHORT press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @param shortPressAvailable True if support otherwise False.
     */
    public ButtonCapabilities setShortPressAvailable(@NonNull Boolean shortPressAvailable) {
        setValue(KEY_SHORT_PRESS_AVAILABLE, shortPressAvailable);
        return this;
    }
    /**
     * Whether the button supports a LONG press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @return True if support otherwise False.
     */
    public Boolean getLongPressAvailable() {
        return getBoolean( KEY_LONG_PRESS_AVAILABLE );
    }
    /**
     * Set the button supports a LONG press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @param longPressAvailable True if support otherwise False.
     */
    public ButtonCapabilities setLongPressAvailable(@NonNull Boolean longPressAvailable) {
        setValue(KEY_LONG_PRESS_AVAILABLE, longPressAvailable);
        return this;
    }
    /**
     * Whether the button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
     * @return True if support otherwise False.
     */    
    public Boolean getUpDownAvailable() {
        return getBoolean( KEY_UP_DOWN_AVAILABLE );
    }
    /**
     * Set the button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
     * @param upDownAvailable True if support otherwise False.
     */
    public ButtonCapabilities setUpDownAvailable(@NonNull Boolean upDownAvailable) {
        setValue(KEY_UP_DOWN_AVAILABLE, upDownAvailable);
        return this;
    }

    /**
     * Sets ModuleInfo for this capability
     * @param info the ModuleInfo to be set
     */
    public ButtonCapabilities setModuleInfo( ModuleInfo info) {
        setValue(KEY_MODULE_INFO, info);
        return this;
    }

    /**
     * Gets a ModuleInfo of this capability
     * @return module info of this capability
     */
    public ModuleInfo getModuleInfo() {
        return (ModuleInfo) getObject(ModuleInfo.class, KEY_MODULE_INFO);
    }
}
