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
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.TBTState;

import java.util.Hashtable;

/**
 * <p>Notifies the application of the current TBT client status on the module.</p>
 *
 * <p></p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul><li>Can be sent with FULL, LIMITED or BACKGROUND</li></ul>
 * AudioStreamingState:
 * <ul><li>Any</li></ul>
 * SystemContext:
 * <ul><li>Any</li></ul>
 * </ul>
 * <p></p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>state</td>
 * <td>{@linkplain TBTState}</td>
 * <td>Current state of TBT client.</td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * </table>
 *
 * @since SmartDeviceLink 1.0
 */
public class OnTBTClientState extends RPCNotification {
    public static final String KEY_STATE = "state";

    /**
     * Constructs a newly allocated OnTBTClientState object
     */
    public OnTBTClientState() {
        super(FunctionID.ON_TBT_CLIENT_STATE.toString());
    }

    /**
     * <p>Constructs a newly allocated OnTBTClientState object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public OnTBTClientState(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated OnTBTClientState object
     *
     * @param state current state of TBT client
     */
    public OnTBTClientState(@NonNull TBTState state) {
        this();
        setState(state);
    }

    /**
     * <p>Called to get the current state of TBT client</p>
     *
     * @return {@linkplain TBTState} the current state of TBT client
     */
    public TBTState getState() {
        return (TBTState) getObject(TBTState.class, KEY_STATE);
    }

    /**
     * <p>Called to set the current state of TBT client</p>
     *
     * @param state current state of TBT client
     */
    public OnTBTClientState setState(TBTState state) {
        setParameters(KEY_STATE, state);
        return this;
    }
} // end-class
