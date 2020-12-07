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
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class Coordinate extends RPCStruct {
    public static final String KEY_LATITUDE_DEGREES = "latitudeDegrees";
    public static final String KEY_LONGITUDE_DEGREES = "longitudeDegrees";

    public Coordinate() {
    }

    public Coordinate(Hashtable<String, Object> hash) {
        super(hash);
    }

    public Coordinate(@NonNull Float latitudeDegrees, @NonNull Float longitudeDegrees) {
        this();
        setLatitudeDegrees(latitudeDegrees);
        setLongitudeDegrees(longitudeDegrees);
    }

    public Float getLatitudeDegrees() {
        Object value = getValue(KEY_LATITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public Coordinate setLatitudeDegrees(@NonNull Float latitudeDegrees) {
        setValue(KEY_LATITUDE_DEGREES, latitudeDegrees);
        return this;
    }

    public Float getLongitudeDegrees() {
        Object value = getValue(KEY_LONGITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public Coordinate setLongitudeDegrees(@NonNull Float longitudeDegrees) {
        setValue(KEY_LONGITUDE_DEGREES, longitudeDegrees);
        return this;
    }
}
