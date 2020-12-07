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
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class Temperature extends RPCStruct {
    public static final String KEY_UNIT = "unit";
    public static final String KEY_VALUE = "value";

    public Temperature() {
    }

    public Temperature(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Create the temperature object
     *
     * @param unit  Temperature Unit.
     * @param value Temperature Value in TemperatureUnit specified unit
     */
    public Temperature(@NonNull TemperatureUnit unit, @NonNull Float value) {
        this();
        setUnit(unit);
        setValue(value);
    }

    /**
     * Sets the unit portion of the Temperature class
     *
     * @param unit Temperature Unit.
     */
    public Temperature setUnit(@NonNull TemperatureUnit unit) {
        setValue(KEY_UNIT, unit);
        return this;
    }

    /**
     * Gets the unit portion of the Temperature class
     *
     * @return TemperatureUnit - Temperature Unit.
     */
    public TemperatureUnit getUnit() {
        return (TemperatureUnit) getObject(TemperatureUnit.class, KEY_UNIT);
    }

    /**
     * Gets the value portion of the Temperature class
     *
     * @return Float - Temperature Value in TemperatureUnit specified unit. Range depends on OEM and is not checked by SDL.
     */
    public Float getValue() {
        Object value = getValue(KEY_VALUE);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    /**
     * Sets the value portion of the Temperature class
     *
     * @param value Temperature Value in TemperatureUnit specified unit. Range depends on OEM and is not checked by SDL.
     */
    public Temperature setValue(@NonNull Float value) {
        setValue(KEY_VALUE, value);
        return this;
    }
}
