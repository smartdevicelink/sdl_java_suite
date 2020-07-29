/*
 * Copyright (c) 2020 Livio, Inc.
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

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * @since SmartDeviceLink 7.0.0
 */
public class SubtleAlertResponse extends RPCResponse {
    public static final String KEY_TRY_AGAIN_TIME = "tryAgainTime";

    /**
     * Constructs a new SubtleAlertResponse object
     */
    public SubtleAlertResponse() {
        super(FunctionID.SUBTLE_ALERT.toString());
    }

    /**
     * Constructs a new SubtleAlertResponse object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public SubtleAlertResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new SubtleAlertResponse object
     *
     * @param success whether the request is successfully processed
     * @param resultCode additional information about a response returning a failed outcome
     */
    public SubtleAlertResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    /**
     * Sets the tryAgainTime.
     *
     * @param tryAgainTime Amount of time (in milliseconds) that an app must wait before resending an alert. If
     * provided, another system event or overlay currently has a higher priority than this alert.
     * An app must not send an alert without waiting at least the amount of time dictated.
     */
    public void setTryAgainTime(Integer tryAgainTime) {
        setParameters(KEY_TRY_AGAIN_TIME, tryAgainTime);
    }

    /**
     * Gets the tryAgainTime.
     *
     * @return Integer Amount of time (in milliseconds) that an app must wait before resending an alert. If
     * provided, another system event or overlay currently has a higher priority than this alert.
     * An app must not send an alert without waiting at least the amount of time dictated.
     */
    public Integer getTryAgainTime() {
        return getInteger(KEY_TRY_AGAIN_TIME);
    }
}
