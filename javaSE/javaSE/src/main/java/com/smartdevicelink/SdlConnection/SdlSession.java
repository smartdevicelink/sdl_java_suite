/*
 * Copyright (c) 2019 Livio, Inc.
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

package com.smartdevicelink.SdlConnection;


import androidx.annotation.RestrictTo;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SdlSession extends BaseSdlSession {

    private static final String TAG = "SdlSession";


    public SdlSession(ISdlSessionListener listener, BaseTransportConfig config) {
        super(listener, config);
    }

    @Override
    protected SdlProtocolBase getSdlProtocolImplementation() {
        return new SdlProtocol(this, transportConfig);
    }

    @Override
    public void onServiceStarted(SdlPacket packet, SessionType serviceType, int sessionID, Version version, boolean isEncrypted) {
        DebugTool.logInfo(TAG, serviceType.getName() + " service started");

        if (serviceType != null && serviceType.eq(SessionType.RPC) && this.sessionId == -1) {
            this.sessionId = sessionID;
            this.sessionListener.onSessionStarted(sessionID, version);
        }

        if (isEncrypted) {
            encryptedServices.addIfAbsent(serviceType);
        }

        if (serviceListeners != null && serviceListeners.containsKey(serviceType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(serviceType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceStarted(this, serviceType, isEncrypted);
            }
        }
    }

    @Override
    public void onServiceEnded(SdlPacket packet, SessionType serviceType, int sessionID) {

        if (SessionType.RPC.equals(serviceType)) {
            this.sessionListener.onSessionEnded(sessionID);
        }

        if (serviceListeners != null && serviceListeners.containsKey(serviceType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(serviceType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceEnded(this, serviceType);
            }
        }

        encryptedServices.remove(serviceType);
    }

    @Override
    public void onServiceError(SdlPacket packet, SessionType sessionType, int sessionID, String error) {
        if (serviceListeners != null && serviceListeners.containsKey(sessionType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceError(this, sessionType, "End " + sessionType.toString() + " Service NACK'ed");
            }
        }
    }

}