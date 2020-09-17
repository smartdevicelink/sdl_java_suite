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
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class GetInteriorVehicleDataResponse extends RPCResponse {
    public static final String KEY_MODULE_DATA = "moduleData";
    public static final String KEY_IS_SUBSCRIBED = "isSubscribed";

    /**
     * Constructs a new GetInteriorVehicleDataResponse object
     */
    public GetInteriorVehicleDataResponse() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA.toString());
    }

    /**
     * <p>Constructs a new GetInteriorVehicleDataResponse object indicated by the
     * Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public GetInteriorVehicleDataResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetInteriorVehicleDataResponse object
     *
     * @param resultCode whether the request is successfully processed
     * @param success    whether the request is successfully processed
     */
    public GetInteriorVehicleDataResponse(@NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setResultCode(resultCode);
        setSuccess(success);
    }

    /**
     * Gets the moduleData
     *
     * @return ModuleData
     */
    public ModuleData getModuleData() {
        return (ModuleData) getObject(ModuleData.class, KEY_MODULE_DATA);
    }

    /**
     * Sets the moduleData
     *
     * @param moduleData specific data for the module that was requested
     */
    public GetInteriorVehicleDataResponse setModuleData(ModuleData moduleData) {
        setParameters(KEY_MODULE_DATA, moduleData);
        return this;
    }

    /**
     * Sets isSubscribed parameter
     *
     * @param isSubscribed It is a conditional-mandatory parameter: must be returned in case "subscribe" parameter was present in the related request.
     *                     If "true" - the "moduleType" from request is successfully subscribed and the head unit will send onInteriorVehicleData notifications for the moduleType.
     *                     If "false" - the "moduleType" from request is either unsubscribed or failed to subscribe.
     */
    public GetInteriorVehicleDataResponse setIsSubscribed(Boolean isSubscribed) {
        setParameters(KEY_IS_SUBSCRIBED, isSubscribed);
        return this;
    }

    /**
     * Gets isSubscribed parameter
     *
     * @return Boolean - It is a conditional-mandatory parameter: must be returned in case "subscribe" parameter was present in the related request.
     * If "true" - the "moduleType" from request is successfully subscribed and the head unit will send onInteriorVehicleData notifications for the moduleType.
     * If "false" - the "moduleType" from request is either unsubscribed or failed to subscribe.
     */
    public Boolean getIsSubscribed() {
        return getBoolean(KEY_IS_SUBSCRIBED);
    }
}
