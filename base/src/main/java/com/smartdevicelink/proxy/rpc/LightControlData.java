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

import java.util.Hashtable;
import java.util.List;

public class LightControlData extends RPCStruct {
    public static final String KEY_LIGHT_STATE = "lightState";

    /**
     * Constructs a new LightControlData object
     */
    public LightControlData() {
    }

    /**
     * <p>Constructs a new LightControlData object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash The Hashtable to use
     */
    public LightControlData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated LightControlData object
     *
     * @param lightState An array of LightNames and their current or desired status.
     *                   Status of the LightNames that are not listed in the array shall remain unchanged.
     */
    public LightControlData(@NonNull List<LightState> lightState) {
        this();
        setLightState(lightState);
    }

    /**
     * Gets the lightState portion of the LightControlData class
     *
     * @return List<LightState> - An array of LightNames and their current or desired status. Status of the LightNames that are not listed in the array shall remain unchanged.
     */
    @SuppressWarnings("unchecked")
    public List<LightState> getLightState() {
        return (List<LightState>) getObject(LightState.class, KEY_LIGHT_STATE);
    }

    /**
     * Sets the lightState portion of the LightControlData class
     *
     * @param lightState An array of LightNames and their current or desired status. Status of the LightNames that are not listed in the array shall remain unchanged.
     */
    public LightControlData setLightState(@NonNull List<LightState> lightState) {
        setValue(KEY_LIGHT_STATE, lightState);
        return this;
    }
}
