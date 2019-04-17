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
import com.smartdevicelink.util.Version;

import java.util.Hashtable;

/**
 * Delete File Response is sent, when DeleteFile has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class DeleteFileResponse extends RPCResponse {
	public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";
	private static final Integer MAX_VALUE = 2000000000;

    /**
     * Constructs a new DeleteFileResponse object
     */
    public DeleteFileResponse() {
        super(FunctionID.DELETE_FILE.toString());
    }

    public DeleteFileResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * @deprecated use {@link DeleteFileResponse#DeleteFileResponse(Boolean, Result)} instead <br>
     *
     * Constructs a new DeleteFileResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     * @param spaceAvailable  the total local space available on the module for the registered app.
     */
    @Deprecated
    public DeleteFileResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull Integer spaceAvailable) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setSpaceAvailable(spaceAvailable);
    }

    /**
     * Constructs a new DeleteFileResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DeleteFileResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    /**
     * SpaceAvailable became optional as of RPC Spec 5.0. If a system that expected the value to
     * always have a value connects to such a system, it could return null. Check to see if there
     * is a value, and if not, set it to MAX_VALUE as defined by the RPC Spec
     *
     * @param rpcVersion the rpc spec version that has been negotiated. If value is null the
     *                   the max value of RPC spec version this library supports should be used.
     * @param formatParams if true, the format method will be called on subsequent params
     */
    @Override
    public void format(Version rpcVersion, boolean formatParams){
        if (rpcVersion == null || rpcVersion.getMajor() >= 5){
            if (getSpaceAvailable() == null){
                setSpaceAvailable(MAX_VALUE);
            }
        }
        super.format(rpcVersion, formatParams);
    }

    public void setSpaceAvailable(Integer spaceAvailable) {
        setParameters(KEY_SPACE_AVAILABLE, spaceAvailable);
    }

    public Integer getSpaceAvailable() {
        return getInteger(KEY_SPACE_AVAILABLE);
    }

}
