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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * When this request is invoked, the audio capture stops
 *
 * <p>Function Group: AudioPassThru</p>
 *
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 *
 * <p><b>Request</b></p>
 *
 * <p>No parameters.</p>
 * <p><b>Response</b> </p>
 * <p><b>Non-default Result Codes:</b></p>
 * <p>SUCCESS</p>
 * <p>INVALID_DATA</p>
 * <p>OUT_OF_MEMORY</p>
 * <p>TOO_MANY_PENDING_REQUESTS</p>
 * <p>APPLICATION_NOT_REGISTERED</p>
 * <p>GENERIC_ERROR</p>
 * <p>REJECTED</p>
 * <p>DISALLOWED</p>
 *
 * @see PerformAudioPassThru
 * @since SmartDeviceLink 2.0
 */
public class EndAudioPassThru extends RPCRequest {

    /**
     * Constructs a new EndAudioPassThru object
     */
    public EndAudioPassThru() {
        super(FunctionID.END_AUDIO_PASS_THRU.toString());
    }

    /**
     * <p>Constructs a new EndAudioPassThru object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public EndAudioPassThru(Hashtable<String, Object> hash) {
        super(hash);
    }
}
