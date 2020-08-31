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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;

import java.util.Hashtable;

/**
 * This function allows a remote control type mobile application
 * simulate a hardware button press event.
 */
public class ButtonPress extends RPCRequest {
	public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_BUTTON_NAME = "buttonName";
    public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";
    public static final String KEY_MODULE_ID = "moduleId";

    /**
     * Constructs a new ButtonPress object
     */
    public ButtonPress() {
        super(FunctionID.BUTTON_PRESS.toString());
    }

    /**
     * <p>Constructs a new ButtonPress object indicated by the
     * Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public ButtonPress(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ButtonPress object
     * @param moduleType Represents module where the button should be pressed
     * @param buttonName Represents name of supported RC climate or radio button
     * @param buttonPressMode Indicates whether this is a LONG or SHORT button press event.
     */
    public ButtonPress(@NonNull ModuleType moduleType, @NonNull ButtonName buttonName, @NonNull ButtonPressMode buttonPressMode) {
        this();
        setModuleType(moduleType);
        setButtonName(buttonName);
        setButtonPressMode(buttonPressMode);
    }

    /**
     * Gets the ModuleType
     *
     * @return ModuleType - The module where the button should be pressed
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets a ModuleType
     *
     * @param moduleType
     * Represents module where the button should be pressed
     */
    public ButtonPress setModuleType(@NonNull ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
        return this;
    }

    /**
     * Gets the ButtonName
     *
     * @return ButtonName - The name of supported RC climate or radio button
     */
    public ButtonName getButtonName() {
        return (ButtonName) getObject(ButtonName.class, KEY_BUTTON_NAME);
    }

    /**
     * Sets a ButtonName
     *
     * @param buttonName
     * Represents name of supported RC climate or radio button
     */
    public ButtonPress setButtonName(@NonNull ButtonName buttonName) {
        setParameters(KEY_BUTTON_NAME, buttonName);
        return this;
    }

    /**
     * Gets the ButtonPressMode
     *
     * @return ButtonPressMode - Indicates whether this is a LONG or SHORT button press event.
     */
    public ButtonPressMode getButtonPressMode() {
        return (ButtonPressMode) getObject(ButtonPressMode.class, KEY_BUTTON_PRESS_MODE);
    }

    /**
     * Sets a ButtonPressMode
     *
     * @param buttonPressMode
     * Indicates whether this is a LONG or SHORT button press event.
     */
    public ButtonPress setButtonPressMode(@NonNull ButtonPressMode buttonPressMode) {
        setParameters(KEY_BUTTON_PRESS_MODE, buttonPressMode);
        return this;
    }

    /**
     * Sets the module id for this object
     * @param id the id to be set
     */
    public ButtonPress setModuleId( String id) {
        setParameters(KEY_MODULE_ID, id);
        return this;
    }

    /**
     * Gets the module id of this object
     * @return the module id of this object
     */
    public String getModuleId() {
        return getString(KEY_MODULE_ID);
    }
}
