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

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.List;

/**
 * PermissionElement holds the RPC name that the developer wants to add a listener for.
 * It also holds any permission parameters for that RPC that the developer wants to track as well.
 */
public class PermissionElement {
    private final FunctionID rpcName;
    private final List<String> parameters;

    /**
     * Create a new instance of PermissionElement
     * @param rpcName the name of the RPC
     * @param parameters a list of the RPC parameters
     */
    public PermissionElement(@NonNull FunctionID rpcName, List<String> parameters){
        this.rpcName = rpcName;
        this.parameters = parameters;
    }

    /**
     * Get the RPC name
     * @return FunctionID value represents the RPC name
     */
    public FunctionID getRPCName() {
        return rpcName;
    }

    /**
     * Get the permission parameters for the RPC
     * @return List<String> represents the permission parameters for the RPC
     */
    public List<String> getParameters() {
        return parameters;
    }
}
