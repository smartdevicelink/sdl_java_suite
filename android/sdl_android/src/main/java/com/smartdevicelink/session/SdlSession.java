/*
 * Copyright (c) 2017-2020 Livio, Inc.
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

package com.smartdevicelink.session;

import android.content.Context;

import androidx.annotation.RestrictTo;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.MediaStreamingStatus;
import com.smartdevicelink.util.SystemInfo;
import com.smartdevicelink.util.Version;

import java.lang.ref.WeakReference;
import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public class SdlSession extends BaseSdlSession {
    private static final String TAG = "SdlSession";

    WeakReference<Context> contextWeakReference;
    MediaStreamingStatus mediaStreamingStatus;
    boolean requiresAudioSupport = false;

    public SdlSession(ISdlSessionListener listener, MultiplexTransportConfig config) {
        super(listener, config);
        if (config != null) {
            contextWeakReference = new WeakReference<>(config.getContext());
            this.requiresAudioSupport = Boolean.TRUE.equals(config.requiresAudioSupport()); //handle null case

        }
        this.sessionListener = listener;

    }

    public SdlSession(ISdlSessionListener listener, TCPTransportConfig config) {
        super(listener, config);
        this.sessionListener = listener;
    }

    protected SdlProtocolBase getSdlProtocolImplementation() {
        if (transportConfig instanceof MultiplexTransportConfig) {
            return new SdlProtocol(this, (MultiplexTransportConfig) transportConfig);
        } else if (transportConfig instanceof TCPTransportConfig) {
            return new SdlProtocol(this, (TCPTransportConfig) transportConfig);
        }

        return null;
    }

    boolean isAudioRequirementMet() {
        if (mediaStreamingStatus == null && contextWeakReference != null && contextWeakReference.get() != null) {
            mediaStreamingStatus = new MediaStreamingStatus(contextWeakReference.get(), new MediaStreamingStatus.Callback() {
                @Override
                public void onAudioNoLongerAvailable() {
                    close();
                    shutdown("Audio output no longer available");
                }
            });
        }

        // If requiresAudioSupport is false, or a supported audio output device is available
        return !requiresAudioSupport || (mediaStreamingStatus != null && mediaStreamingStatus.isAudioOutputAvailable());
    }


    @SuppressWarnings("RedundantThrows")
    @Override
    public void startSession() throws SdlException {
        if (!isAudioRequirementMet()) {
            shutdown("Audio output not available");
            return;
        }

        sdlProtocol.start();
    }

    @Override
    public TransportType getCurrentTransportType() {
        return TransportType.MULTIPLEX;
    }

    @Override
    public void shutdown(String info) {
        DebugTool.logInfo(TAG, "Shutdown - " + info);
        if (mediaStreamingStatus != null) {
            mediaStreamingStatus.clear();
        }
        super.shutdown(info);

    }

    /**
     * Get the current protocol version used by this session
     *
     * @return Version that represents the Protocol version being used
     */
    @Override
    public Version getProtocolVersion() {
        if (sdlProtocol != null) {
            return sdlProtocol.getProtocolVersion();
        }
        return new Version(1, 0, 0);
    }


    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  ISdlProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

    @Override
    public void onServiceStarted(SdlPacket packet, SessionType serviceType, int sessionID, Version version, boolean isEncrypted) {
        DebugTool.logInfo(TAG, serviceType.getName() + " service started");

        if (serviceType != null && serviceType.eq(SessionType.RPC) && this.sessionId == -1) {
            this.sessionId = sessionID;
            SystemInfo systemInfo = null;
            if(version != null && version.isNewerThan(new Version(5,4,0)) >= 0) {
                systemInfo = extractSystemInfo(packet);
            }
            this.sessionListener.onSessionStarted(sessionID, version, systemInfo);
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
    public void onServiceError(SdlPacket packet, SessionType serviceType, int sessionID, String error) {
        if (serviceListeners != null && serviceListeners.containsKey(serviceType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(serviceType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceError(this, serviceType, error);
            }
        }
    }

    @Override
    public void onAuthTokenReceived(String authToken) {/* Do nothing */ }

}
