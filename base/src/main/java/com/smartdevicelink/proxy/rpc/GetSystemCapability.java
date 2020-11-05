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
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import java.util.Hashtable;

/**
 * Used to request the corresponding capability object for a given capability.
 */

public class GetSystemCapability extends RPCRequest {
    public static final String KEY_SYSTEM_CAPABILITY_TYPE = "systemCapabilityType";
    public static final String KEY_SUBSCRIBE = "subscribe";

    /**
     * Constructs a new GetSystemCapability object
     */
    public GetSystemCapability() {
        super(FunctionID.GET_SYSTEM_CAPABILITY.toString());
    }

    /**
     * <p>Constructs a new GetSystemCapability object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public GetSystemCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new GetSystemCapability object
     *
     * @param systemCapabilityType SystemCapabilityType being requested
     */
    public GetSystemCapability(@NonNull SystemCapabilityType systemCapabilityType) {
        this();
        setSystemCapabilityType(systemCapabilityType);
    }

    /**
     * Used to get the SystemCapabilityType being requested
     *
     * @return the SystemCapabilityType being requested
     */
    public SystemCapabilityType getSystemCapabilityType() {
        return (SystemCapabilityType) getObject(SystemCapabilityType.class, KEY_SYSTEM_CAPABILITY_TYPE);
    }

    /**
     * Used to set the SystemCapabilityType being requested
     *
     * @param value SystemCapabilityType being requested
     */
    public GetSystemCapability setSystemCapabilityType(@NonNull SystemCapabilityType value) {
        setParameters(KEY_SYSTEM_CAPABILITY_TYPE, value);
        return this;
    }

    /**
     * Flag to subscribe to updates of the supplied service capability type. If true, the requester
     * will be subscribed. If false, the requester will not be subscribed and be removed as a
     * subscriber if it was previously subscribed.
     *
     * @return if the SystemCapabilityType is subscribed to
     */
    public Boolean getSubscribe() {
        return getBoolean(KEY_SUBSCRIBE);
    }

    /**
     * Flag to subscribe to updates of the supplied service capability type. If true, the requester
     * will be subscribed. If false, the requester will not be subscribed and be removed as a
     * subscriber if it was previously subscribed.
     *
     * @param subscribe to changes in the SystemCapabilityType
     */
    public GetSystemCapability setSubscribe(Boolean subscribe) {
        setParameters(KEY_SUBSCRIBE, subscribe);
        return this;
    }
}
