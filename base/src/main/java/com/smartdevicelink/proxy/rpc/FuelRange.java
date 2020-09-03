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
import com.smartdevicelink.proxy.rpc.enums.CapacityUnit;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
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
 *      <td>type</td>
 *      <td>FuelType</td>
 *      <td></td>
 *      <td>N</td>
 *      <td></td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>range</td>
 *      <td>Float</td>
 *      <td>The estimate range in KM the vehicle can travel based on fuel level and consumption.</td>
 *      <td>N</td>
 *      <td>{"num_min_value": 0.0, "num_max_value": 10000.0}</td>
 *      <td></td>
 *  </tr>
 *  <tr>
 *      <td>level</td>
 *      <td>Float</td>
 *      <td>The relative remaining capacity of this fuel type (percentage).</td>
 *      <td>N</td>
 *      <td>{"num_min_value": -6.0, "num_max_value": 1000000.0}</td>
 *      <td>SmartDeviceLink 7.0.0</td>
 *  </tr>
 *  <tr>
 *      <td>levelState</td>
 *      <td>ComponentVolumeStatus</td>
 *      <td>The fuel level state</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>SmartDeviceLink 7.0.0</td>
 *  </tr>
 *  <tr>
 *      <td>capacity</td>
 *      <td>Float</td>
 *      <td>The absolute capacity of this fuel type.</td>
 *      <td>N</td>
 *      <td>{"num_min_value": 0.0, "num_max_value": 1000000.0}</td>
 *      <td>SmartDeviceLink 7.0.0</td>
 *  </tr>
 *  <tr>
 *      <td>capacityUnit</td>
 *      <td>CapacityUnit</td>
 *      <td>The unit of the capacity of this fuel type such as liters for gasoline or kWh forbatteries.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>SmartDeviceLink 7.0.0</td>
 *  </tr>
 * </table>
 * @since SmartDeviceLink 5.0.0
 */
public class FuelRange extends RPCStruct {
    public static final String KEY_TYPE = "type";
    public static final String KEY_RANGE = "range";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_LEVEL_STATE = "levelState";
    public static final String KEY_CAPACITY = "capacity";
    public static final String KEY_CAPACITY_UNIT = "capacityUnit";

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
     * @see FuelType
     */
    public FuelRange setType( FuelType fuelType) {
        setValue(KEY_TYPE, fuelType);
        return this;
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
    public FuelRange setRange( Float range) {
        setValue(KEY_RANGE, range);
        return this;
    }

    /**
     * Sets the level.
     *
     * @param level The relative remaining capacity of this fuel type (percentage).
     * {"num_min_value": -6.0, "num_max_value": 1000000.0}
     * @since SmartDeviceLink 7.0.0
     */
    public FuelRange setLevel( Float level) {
        setValue(KEY_LEVEL, level);
        return this;
    }

    /**
     * Gets the level.
     *
     * @return Float The relative remaining capacity of this fuel type (percentage).
     * {"num_min_value": -6.0, "num_max_value": 1000000.0}
     * @since SmartDeviceLink 7.0.0
     */
    public Float getLevel() {
        Object object = getValue(KEY_LEVEL);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the levelState.
     *
     * @param levelState The fuel level state
     * @since SmartDeviceLink 7.0.0
     */
    public FuelRange setLevelState( ComponentVolumeStatus levelState) {
        setValue(KEY_LEVEL_STATE, levelState);
        return this;
    }

    /**
     * Gets the levelState.
     *
     * @return ComponentVolumeStatus The fuel level state
     * @since SmartDeviceLink 7.0.0
     */
    public ComponentVolumeStatus getLevelState() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_LEVEL_STATE);
    }

    /**
     * Sets the capacity.
     *
     * @param capacity The absolute capacity of this fuel type.
     * {"num_min_value": 0.0, "num_max_value": 1000000.0}
     * @since SmartDeviceLink 7.0.0
     */
    public FuelRange setCapacity( Float capacity) {
        setValue(KEY_CAPACITY, capacity);
        return this;
    }

    /**
     * Gets the capacity.
     *
     * @return Float The absolute capacity of this fuel type.
     * {"num_min_value": 0.0, "num_max_value": 1000000.0}
     * @since SmartDeviceLink 7.0.0
     */
    public Float getCapacity() {
        Object object = getValue(KEY_CAPACITY);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the capacityUnit.
     *
     * @param capacityUnit The unit of the capacity of this fuel type such as liters for gasoline or kWh for
     * batteries.
     * @since SmartDeviceLink 7.0.0
     */
    public FuelRange setCapacityUnit( CapacityUnit capacityUnit) {
        setValue(KEY_CAPACITY_UNIT, capacityUnit);
        return this;
    }

    /**
     * Gets the capacityUnit.
     *
     * @return CapacityUnit The unit of the capacity of this fuel type such as liters for gasoline or kWh for
     * batteries.
     * @since SmartDeviceLink 7.0.0
     */
    public CapacityUnit getCapacityUnit() {
        return (CapacityUnit) getObject(CapacityUnit.class, KEY_CAPACITY_UNIT);
    }
}
