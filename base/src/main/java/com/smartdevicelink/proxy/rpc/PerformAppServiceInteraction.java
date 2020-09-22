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

import java.util.Hashtable;

public class PerformAppServiceInteraction extends RPCRequest {

    public static final String KEY_SERVICE_URI = "serviceUri";
    public static final String KEY_SERVICE_ID = "serviceID";
    public static final String KEY_ORIGIN_APP = "originApp";
    public static final String KEY_REQUEST_SERVICE_ACTIVE = "requestServiceActive";

    // Constructors

    public PerformAppServiceInteraction() {
        super(FunctionID.PERFORM_APP_SERVICES_INTERACTION.toString());
    }

    public PerformAppServiceInteraction(Hashtable<String, Object> hash) {
        super(hash);
    }

    public PerformAppServiceInteraction(@NonNull String serviceUri, @NonNull String appServiceId, @NonNull String originApp) {
        this();
        setServiceUri(serviceUri);
        setServiceID(appServiceId);
        setOriginApp(originApp);
    }

    /**
     * Fully qualified URI based on a predetermined scheme provided by the app service. SDL makes no guarantee that this
     * URI is correct.
     *
     * @param serviceUri -
     */
    public PerformAppServiceInteraction setServiceUri(@NonNull String serviceUri) {
        setParameters(KEY_SERVICE_URI, serviceUri);
        return this;
    }

    /**
     * Fully qualified URI based on a predetermined scheme provided by the app service. SDL makes no guarantee that this
     * URI is correct.
     *
     * @return serviceUri
     */
    public String getServiceUri() {
        return getString(KEY_SERVICE_URI);
    }

    /**
     * The service ID that the app consumer wishes to send this URI.
     *
     * @param appServiceId -
     */
    public PerformAppServiceInteraction setServiceID(@NonNull String appServiceId) {
        setParameters(KEY_SERVICE_ID, appServiceId);
        return this;
    }

    /**
     * The service ID that the app consumer wishes to send this URI.
     *
     * @return appServiceId
     */
    public String getServiceID() {
        return getString(KEY_SERVICE_ID);
    }

    /**
     * This string is the appID of the app requesting the app service provider take the specific action.
     *
     * @param originApp -
     */
    public PerformAppServiceInteraction setOriginApp(@NonNull String originApp) {
        setParameters(KEY_ORIGIN_APP, originApp);
        return this;
    }

    /**
     * This string is the appID of the app requesting the app service provider take the specific action.
     *
     * @return originApp
     */
    public String getOriginApp() {
        return getString(KEY_ORIGIN_APP);
    }

    /**
     * This flag signals the requesting consumer would like this service to become the active primary
     * service of the destination's type.
     *
     * @param requestServiceActive -
     */
    public PerformAppServiceInteraction setRequestServiceActive(Boolean requestServiceActive) {
        setParameters(KEY_REQUEST_SERVICE_ACTIVE, requestServiceActive);
        return this;
    }

    /**
     * This string is the appID of the app requesting the app service provider take the specific action.
     *
     * @return requestServiceActive
     */
    public Boolean getRequestServiceActive() {
        return getBoolean(KEY_REQUEST_SERVICE_ACTIVE);
    }

}
