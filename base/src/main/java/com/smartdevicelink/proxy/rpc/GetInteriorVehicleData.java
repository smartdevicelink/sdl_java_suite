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
import com.smartdevicelink.proxy.rpc.enums.ModuleType;

import java.util.Hashtable;

/**
 * Read the current status value of specified remote control module (type). In addition,
 * When subscribe=true, subscribes for specific remote control module data items;
 * When subscribe=false, un-subscribes for specific remote control module data items.
 * Once subscribed, the application will be notified by the onInteriorVehicleData notification
 * whenever new data is available for the module.
 */
public class GetInteriorVehicleData extends RPCRequest {
    public static final String KEY_MODULE_TYPE = "moduleType";
    public static final String KEY_SUBSCRIBE = "subscribe";
    public static final String KEY_MODULE_ID = "moduleId";

    /**
     * Constructs a new GetInteriorVehicleData object
     */
    public GetInteriorVehicleData() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }

    /**
     * <p>Constructs a new GetInteriorVehicleData object indicated by the
     * Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public GetInteriorVehicleData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetInteriorVehicleData object
     */
    public GetInteriorVehicleData(@NonNull ModuleType moduleType) {
        this();
        setModuleType(moduleType);
    }

    /**
     * Gets the ModuleType
     *
     * @return ModuleType - The type of a RC module to retrieve module data from the vehicle.
     * In the future, this should be the Identification of a module.
     */
    public ModuleType getModuleType() {
        return (ModuleType) getObject(ModuleType.class, KEY_MODULE_TYPE);
    }

    /**
     * Sets a ModuleType
     *
     * @param moduleType The type of a RC module to retrieve module data from the vehicle.
     *                   In the future, this should be the Identification of a module.
     */
    public GetInteriorVehicleData setModuleType(@NonNull ModuleType moduleType) {
        setParameters(KEY_MODULE_TYPE, moduleType);
        return this;
    }

    /**
     * Sets subscribe parameter
     *
     * @param subscribe If subscribe is true, the head unit will register onInteriorVehicleData notifications for the requested moduleType.
     *                  If subscribe is false, the head unit will unregister onInteriorVehicleData notifications for the requested moduleType.
     */
    public GetInteriorVehicleData setSubscribe(Boolean subscribe) {
        setParameters(KEY_SUBSCRIBE, subscribe);
        return this;
    }

    /**
     * Gets subscribe parameter
     *
     * @return Boolean - If subscribe is true, the head unit will register onInteriorVehicleData notifications for the requested moduelType.
     * If subscribe is false, the head unit will unregister onInteriorVehicleData notifications for the requested moduelType.
     */
    public Boolean getSubscribe() {
        return getBoolean(KEY_SUBSCRIBE);
    }

    /**
     * Sets the Module ID for this class
     *
     * @param id the id to be set
     */
    public GetInteriorVehicleData setModuleId(String id) {
        setParameters(KEY_MODULE_ID, id);
        return this;
    }

    /**
     * Gets the Module ID of this class
     *
     * @return the Module ID of this class
     */
    public String getModuleId() {
        return getString(KEY_MODULE_ID);
    }
}
