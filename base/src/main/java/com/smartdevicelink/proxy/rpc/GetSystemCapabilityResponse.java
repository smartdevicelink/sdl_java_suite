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

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * GetSystemCapabilityResponse is sent, when GetSystemCapability has been called
 */

public class GetSystemCapabilityResponse extends RPCResponse {
    public static final String KEY_SYSTEM_CAPABILITY = "systemCapability";

    /**
     * Constructs a new GetSystemCapability object
     */
    public GetSystemCapabilityResponse(){
        super(FunctionID.GET_SYSTEM_CAPABILITY.toString());
    }

    /**
     * <p>Constructs a new GetSystemCapability object indicated by the Hashtable parameter</p>
     *
     *
     * @param hash
     *            The Hashtable to use
     */
    public GetSystemCapabilityResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetSystemCapabilityResponse object
     * @param systemCapability SystemCapability object
     * @param resultCode whether the request is successfully processed
     * @param success whether the request is successfully processed
     */
    public GetSystemCapabilityResponse(@NonNull SystemCapability systemCapability, @NonNull Result resultCode, @NonNull Boolean success) {
        this();
        setSystemCapability(systemCapability);
        setResultCode(resultCode);
        setSuccess(success);
    }

    /**
     * Get the SystemCapability object returned after a GetSystemCapability call
     * @return SystemCapability object
     */
    public SystemCapability getSystemCapability(){
        return (SystemCapability) getObject(SystemCapability.class, KEY_SYSTEM_CAPABILITY);
    }

    /**
     * Set a SystemCapability object in the response
     * @param value SystemCapability object
     */
    public void setSystemCapability(@NonNull SystemCapability value){
        setParameters(KEY_SYSTEM_CAPABILITY, value);
    }
}
