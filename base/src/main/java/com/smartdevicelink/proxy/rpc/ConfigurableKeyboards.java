/*
 * Copyright (c) 2017 - 2020, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium Inc. nor the names of
 * its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
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
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;

import java.util.Hashtable;

/**
 * Describes number of cofigurable Keys for Special characters.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>keyboardLayout</td>
 *      <td>KeyboardLayout</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>numConfigurableKeys</td>
 *      <td>Integer</td>
 *      <td></td>
 *      <td>Y</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 7.1.0
 */
public class ConfigurableKeyboards extends RPCStruct {
    public static final String KEY_KEYBOARD_LAYOUT = "keyboardLayout";
    public static final String KEY_NUM_CONFIGURABLE_KEYS = "numConfigurableKeys";

    /**
     * Constructs a new ConfigurableKeyboards object
     */
    public ConfigurableKeyboards() { }

    /**
     * Constructs a new ConfigurableKeyboards object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public ConfigurableKeyboards(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new ConfigurableKeyboards object
     *
     * @param keyboardLayout
     * @param numConfigurableKeys
     */
    public ConfigurableKeyboards(@NonNull KeyboardLayout keyboardLayout, @NonNull Integer numConfigurableKeys) {
        this();
        setKeyboardLayout(keyboardLayout);
        setNumConfigurableKeys(numConfigurableKeys);
    }

    /**
     * Sets the keyboardLayout.
     *
     * @param keyboardLayout
     */
    public ConfigurableKeyboards setKeyboardLayout(@NonNull KeyboardLayout keyboardLayout) {
        setValue(KEY_KEYBOARD_LAYOUT, keyboardLayout);
        return this;
    }

    /**
     * Gets the keyboardLayout.
     *
     * @return KeyboardLayout
     */
    public KeyboardLayout getKeyboardLayout() {
        return (KeyboardLayout) getObject(KeyboardLayout.class, KEY_KEYBOARD_LAYOUT);
    }

    /**
     * Sets the numConfigurableKeys.
     *
     * @param numConfigurableKeys
     */
    public ConfigurableKeyboards setNumConfigurableKeys(@NonNull Integer numConfigurableKeys) {
        setValue(KEY_NUM_CONFIGURABLE_KEYS, numConfigurableKeys);
        return this;
    }

    /**
     * Gets the numConfigurableKeys.
     *
     * @return Integer
     */
    public Integer getNumConfigurableKeys() {
        return getInteger(KEY_NUM_CONFIGURABLE_KEYS);
    }
}
