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
import java.util.List;

/**
 * Diagnostic Message Response is sent, when DiagnosticMessage has been called.
 * 
 * @since SmartDeviceLink 3.0
 */
public class DiagnosticMessageResponse extends RPCResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";
	/** 
	 * Constructs a new DiagnosticMessageResponse object
	 */

    public DiagnosticMessageResponse() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }
    public DiagnosticMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new DiagnosticMessageResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @deprecated use {@link DiagnosticMessageResponse#DiagnosticMessageResponse(Boolean, Result)}
     */
    @Deprecated
    public DiagnosticMessageResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull List<Integer> messageDataResult) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setMessageDataResult(messageDataResult);
    }

    /**
     * Constructs a new DiagnosticMessageResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DiagnosticMessageResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMessageDataResult() {
        return (List<Integer>) getObject(Integer.class, KEY_MESSAGE_DATA_RESULT);
    }
    
    public void setMessageDataResult(List<Integer> messageDataResult) {
        setParameters(KEY_MESSAGE_DATA_RESULT, messageDataResult);
    }


}
