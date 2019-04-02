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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class FuelRange extends RPCStruct{
    public static final String KEY_TYPE = "type";
    public static final String KEY_RANGE = "range";

    /**
     * Constructs a new FuelRange object
     */
    public FuelRange() { }

    /**
     * <p>Constructs a new FuelRange object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash
     *            The Hashtable to use
     */
    public FuelRange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the type portion of the FuelRange class
     *
     * @param fuelType the type of fuel related to this FuelRange object.
     *
     * @see com.smartdevicelink.proxy.rpc.enums.FuelType
     */
    public void setType(FuelType fuelType) {
        setValue(KEY_TYPE, fuelType);
    }

    /**
     * Gets the type portion of the FuelRange class
     *
     * @return FuelType.
     */
    public FuelType getType() {
        return (FuelType) getObject(FuelType.class, KEY_TYPE);
    }

    /**
     * Gets the range portion of the FuelRange class
     *
     * @return Float - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public Float getRange() {
        Object object = getValue(KEY_RANGE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the range portion of the FuelRange class
     *
     * @param range
     * The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public void setRange(Float range) {
        setValue(KEY_RANGE, range);
    }
}
