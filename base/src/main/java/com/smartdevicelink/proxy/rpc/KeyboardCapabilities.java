/*
 * Copyright (c) 2017 - 2021, SmartDeviceLink Consortium, Inc.
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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

/**
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
 *      <td>maskInputCharactersSupported</td>
 *      <td>Boolean</td>
 *      <td>Availability of capability to mask input characters using keyboard. True: Available,False: Not Available</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 7.1.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>supportedKeyboards</td>
 *      <td>List<KeyboardLayoutCapability></td>
 *      <td>Capabilities of supported keyboard layouts by HMI.</td>
 *      <td>N</td>
 *      <td>{"array_max_size": 1000, "array_min_size": 1}</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.1.0
 */
public class KeyboardCapabilities extends RPCStruct {
    /**
     * @since SmartDeviceLink 7.1.0
     */
    public static final String KEY_MASK_INPUT_CHARACTERS_SUPPORTED = "maskInputCharactersSupported";
    public static final String KEY_SUPPORTED_KEYBOARDS = "supportedKeyboards";

    /**
     * Constructs a new KeyboardCapabilities object
     */
    public KeyboardCapabilities() { }

    /**
     * Constructs a new KeyboardCapabilities object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public KeyboardCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the maskInputCharactersSupported.
     *
     * @param maskInputCharactersSupported Availability of capability to mask input characters using keyboard. True: Available,
     * False: Not Available
     * @since SmartDeviceLink 7.1.0
     */
    public KeyboardCapabilities setMaskInputCharactersSupported(Boolean maskInputCharactersSupported) {
        setValue(KEY_MASK_INPUT_CHARACTERS_SUPPORTED, maskInputCharactersSupported);
        return this;
    }

    /**
     * Gets the maskInputCharactersSupported.
     *
     * @return Boolean Availability of capability to mask input characters using keyboard. True: Available,
     * False: Not Available
     * @since SmartDeviceLink 7.1.0
     */
    public Boolean getMaskInputCharactersSupported() {
        return getBoolean(KEY_MASK_INPUT_CHARACTERS_SUPPORTED);
    }

    /**
     * Sets the supportedKeyboards.
     *
     * @param supportedKeyboards Capabilities of supported keyboard layouts by HMI.
     * {"array_max_size": 1000, "array_min_size": 1}
     */
    public KeyboardCapabilities setSupportedKeyboards(List<KeyboardLayoutCapability> supportedKeyboards) {
        setValue(KEY_SUPPORTED_KEYBOARDS, supportedKeyboards);
        return this;
    }

    /**
     * Gets the supportedKeyboards.
     *
     * @return List<KeyboardLayoutCapability> Capabilities of supported keyboard layouts by HMI.
     * {"array_max_size": 1000, "array_min_size": 1}
     */
    @SuppressWarnings("unchecked")
    public List<KeyboardLayoutCapability> getSupportedKeyboards() {
        return (List<KeyboardLayoutCapability>) getObject(KeyboardLayoutCapability.class, KEY_SUPPORTED_KEYBOARDS);
    }
}
