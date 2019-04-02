/*
 * Copyright (c) 2019, Livio, Inc.
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
package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

/**
 * PermissionStatus gives a detailed view about whether an RPC and its permission parameters are allowed or not
 */
public class PermissionStatus {
    private final FunctionID rpcName;
    private boolean isRPCAllowed;
    private Map<String, Boolean> allowedParameters;

    /**
     * Creates a new PermissionStatus instance
     * @param rpcName
     * @param isRPCAllowed
     * @param allowedParameters
     */
    public PermissionStatus(@NonNull FunctionID rpcName, @NonNull boolean isRPCAllowed, Map<String, Boolean> allowedParameters) {
        this.rpcName = rpcName;
        this.isRPCAllowed = isRPCAllowed;
        this.allowedParameters = allowedParameters;
    }

    /**
     * Get the name of the RPC
     * @return FunctionID value represents the name of the RPC
     */
    public FunctionID getRPCName() {
        return rpcName;
    }

    /**
     * Get whether the RCP is allowed or not
     * @return boolean represents whether the RCP is allowed or not
     */
    public boolean getIsRPCAllowed() {
        return isRPCAllowed;
    }

    /**
     * Set whether the RPC is allowed or not
     * @param isRPCAllowed
     */
    protected void setIsRPCAllowed(@NonNull boolean isRPCAllowed) {
        this.isRPCAllowed = isRPCAllowed;
    }

    /**
     * Get the status of the permission parameter for the RPC
     * @return Map<String, Boolean> object with keys that represent the permission parameter names and values that represent whether the parameters are allowed or not
     */
    public Map<String, Boolean> getAllowedParameters() {
        return allowedParameters;
    }

    /**
     * Set the status of the permission parameter for the RPC
     * @param allowedParameters
     */
    protected void setAllowedParameters(Map<String, Boolean> allowedParameters) {
        this.allowedParameters = allowedParameters;
    }
}
