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
package com.smartdevicelink.proxy;

import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.util.Hashtable;

public class RPCRequest extends RPCMessage {

    protected OnRPCResponseListener onResponseListener;

    public RPCRequest(String functionName) {
        super(functionName, RPCMessage.KEY_REQUEST);
        messageType = RPCMessage.KEY_REQUEST;
    }

    public RPCRequest(Hashtable<String, Object> hash) {
        super(hash);
    }

    public RPCRequest(RPCRequest request) {
        super(request);
        if (request == null || request.getCorrelationID() == null) {
            setCorrelationID(CorrelationIdGenerator.generateId());
        }
    }

    public Integer getCorrelationID() {
        //First we check to see if a correlation ID is set. If not, create one.
        if (!function.containsKey(RPCMessage.KEY_CORRELATION_ID)) {
            setCorrelationID(CorrelationIdGenerator.generateId());
        }
        return (Integer) function.get(RPCMessage.KEY_CORRELATION_ID);
    }

    public RPCRequest setCorrelationID(Integer correlationID) {
        if (correlationID != null) {
            function.put(RPCMessage.KEY_CORRELATION_ID, correlationID);
        } else {
            function.remove(RPCMessage.KEY_CORRELATION_ID);
        }
        return this;
    }

    public RPCRequest setOnRPCResponseListener(OnRPCResponseListener listener) {
        onResponseListener = listener;
        return this;
    }

    public OnRPCResponseListener getOnRPCResponseListener() {
        return this.onResponseListener;
    }
}
