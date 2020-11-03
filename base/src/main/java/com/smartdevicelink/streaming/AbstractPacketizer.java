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
package com.smartdevicelink.streaming;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.session.SdlSession;
import com.smartdevicelink.util.Version;

import java.io.IOException;
import java.io.InputStream;

abstract public class AbstractPacketizer {

    protected IStreamListener _streamListener;
    protected byte _rpcSessionID = 0;

    protected SessionType _serviceType;
    protected SdlSession _session;
    protected InputStream is;
    protected int bufferSize;
    protected byte[] buffer;
    protected RPCRequest _request = null;
    protected Version _wiproVersion = new Version("1.0.0");

    public AbstractPacketizer(IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID, SdlSession session) throws IOException, IllegalArgumentException {
        this._streamListener = streamListener;
        this.is = is;
        _rpcSessionID = rpcSessionID;
        _serviceType = sType;
        this._session = session;
        if (this._session != null) {
            bufferSize = this._session.getMtu();
            buffer = new byte[bufferSize];
        } else {
            throw new IllegalArgumentException("Session variable is null");
        }
    }

    public AbstractPacketizer(IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, Version protocolVersion, SdlSession session) throws IOException, IllegalArgumentException {
        this._streamListener = streamListener;
        this.is = is;
        _rpcSessionID = rpcSessionID;
        _serviceType = sType;
        _request = request;
        _wiproVersion = protocolVersion;
        this._session = session;
        if (this._session != null) {
            bufferSize = this._session.getMtu();
            buffer = new byte[bufferSize];
        } else {
            throw new IllegalArgumentException("Session variable is null");
        }
    }

    public abstract void start() throws IOException;

    public abstract void stop();

    public abstract void pause();

    public abstract void resume();
}