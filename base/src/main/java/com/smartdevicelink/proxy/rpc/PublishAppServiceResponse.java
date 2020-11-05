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
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class PublishAppServiceResponse extends RPCResponse {

    public static final String KEY_APP_SERVICE_RECORD = "appServiceRecord";

    /**
     * Constructs a new PublishAppServiceResponse object
     */
    public PublishAppServiceResponse() {
        super(FunctionID.PUBLISH_APP_SERVICE.toString());
    }

    public PublishAppServiceResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new PublishAppServiceResponse object
     *
     * @param success    whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public PublishAppServiceResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    // Custom Getters / Setters

    /**
     * If the request was successful, this object will be the current status of the service record
     * for the published service. This will include the Core supplied service ID.
     *
     * @param appServiceRecord - the App Service Record
     */
    public PublishAppServiceResponse setServiceRecord(AppServiceRecord appServiceRecord) {
        setParameters(KEY_APP_SERVICE_RECORD, appServiceRecord);
        return this;
    }

    /**
     * If the request was successful, this object will be the current status of the service record
     * for the published service. This will include the Core supplied service ID.
     *
     * @return appServiceRecord - the App Service Record
     */
    public AppServiceRecord getServiceRecord() {
        return (AppServiceRecord) getObject(AppServiceRecord.class, KEY_APP_SERVICE_RECORD);
    }
}