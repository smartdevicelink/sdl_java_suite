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

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

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
 *      <td>externalTemperature</td>
 *      <td>Temperature</td>
 *      <td>The external temperature in degrees celsius</td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>cabinTemperature</td>
 *      <td>Temperature</td>
 *      <td>Internal ambient cabin temperature in degrees celsius</td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>atmosphericPressure</td>
 *      <td>Float</td>
 *      <td>Current atmospheric pressure in mBar</td>
 *      <td>N</td>
 *      <td>{"num_min_value": 0.0, "num_max_value": 2000.0}</td>
 *      <td></td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 7.1.0
 */
public class ClimateData extends RPCStruct {
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_CABIN_TEMPERATURE = "cabinTemperature";
    public static final String KEY_ATMOSPHERIC_PRESSURE = "atmosphericPressure";

    /**
     * Constructs a new ClimateData object
     */
    public ClimateData() { }

    /**
     * Constructs a new ClimateData object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public ClimateData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the externalTemperature.
     *
     * @param externalTemperature The external temperature in degrees celsius
     */
    public ClimateData setExternalTemperature(Temperature externalTemperature) {
        setValue(KEY_EXTERNAL_TEMPERATURE, externalTemperature);
        return this;
    }

    /**
     * Gets the externalTemperature.
     *
     * @return Temperature The external temperature in degrees celsius
     */
    public Temperature getExternalTemperature() {
        return (Temperature) getObject(Temperature.class, KEY_EXTERNAL_TEMPERATURE);
    }

    /**
     * Sets the cabinTemperature.
     *
     * @param cabinTemperature Internal ambient cabin temperature in degrees celsius
     */
    public ClimateData setCabinTemperature(Temperature cabinTemperature) {
        setValue(KEY_CABIN_TEMPERATURE, cabinTemperature);
        return this;
    }

    /**
     * Gets the cabinTemperature.
     *
     * @return Temperature Internal ambient cabin temperature in degrees celsius
     */
    public Temperature getCabinTemperature() {
        return (Temperature) getObject(Temperature.class, KEY_CABIN_TEMPERATURE);
    }

    /**
     * Sets the atmosphericPressure.
     *
     * @param atmosphericPressure Current atmospheric pressure in mBar
     * {"num_min_value": 0.0, "num_max_value": 2000.0}
     */
    public ClimateData setAtmosphericPressure(Float atmosphericPressure) {
        setValue(KEY_ATMOSPHERIC_PRESSURE, atmosphericPressure);
        return this;
    }

    /**
     * Gets the atmosphericPressure.
     *
     * @return Float Current atmospheric pressure in mBar
     * {"num_min_value": 0.0, "num_max_value": 2000.0}
     */
    public Float getAtmosphericPressure() {
        Object object = getValue(KEY_ATMOSPHERIC_PRESSURE);
        return SdlDataTypeConverter.objectToFloat(object);
    }
}
