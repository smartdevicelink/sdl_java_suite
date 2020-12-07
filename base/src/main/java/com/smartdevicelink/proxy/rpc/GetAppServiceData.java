/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

import java.util.Hashtable;

/**
 * This request asks the module for current data related to the specific service.
 * It also includes an option to subscribe to that service for future updates
 */
public class GetAppServiceData extends RPCRequest {

    public static final String KEY_SERVICE_TYPE = "serviceType";
    public static final String KEY_SUBSCRIBE = "subscribe";

    // Constructors

    /**
     * Constructs a new GetAppServiceData object
     */
    public GetAppServiceData() {
        super(FunctionID.GET_APP_SERVICE_DATA.toString());
    }

    /**
     * Constructs a new GetAppServiceData object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public GetAppServiceData(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetAppServiceData object with the mandatory appServiceType parameter
     *
     * @param appServiceType - The appServiceType
     */
    public GetAppServiceData(@NonNull String appServiceType) {
        this();
        setServiceType(appServiceType);
    }

    // Getters / Setters

    /**
     * @param appServiceType - the appServiceType
     */
    public GetAppServiceData setServiceType(@NonNull String appServiceType) {
        setParameters(KEY_SERVICE_TYPE, appServiceType);
        return this;
    }

    /**
     * @return appServiceType
     */
    public String getServiceType() {
        return getString(KEY_SERVICE_TYPE);
    }

    /**
     * If true, the consumer is requesting to subscribe to all future updates from the service
     * publisher. If false, the consumer doesn't wish to subscribe and should be unsubscribed
     * if it was previously subscribed.
     *
     * @param subscribe -
     */
    public GetAppServiceData setSubscribe(Boolean subscribe) {
        setParameters(KEY_SUBSCRIBE, subscribe);
        return this;
    }

    /**
     * If true, the consumer is requesting to subscribe to all future updates from the service
     * publisher. If false, the consumer doesn't wish to subscribe and should be unsubscribed
     * if it was previously subscribed.
     *
     * @return subscribe
     */
    public Boolean getSubscribe() {
        return getBoolean(KEY_SUBSCRIBE);
    }


}
