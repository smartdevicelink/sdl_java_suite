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

package com.smartdevicelink.SdlConnection;

import android.content.Context;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.MediaStreamingStatus;
import com.smartdevicelink.util.Version;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SdlSession extends BaseSdlSession {
    private static final String TAG = "SdlSession";

    WeakReference<Context> contextWeakReference;
    MediaStreamingStatus mediaStreamingStatus;
    boolean requiresAudioSupport = false;

    public SdlSession(ISdlConnectionListener listener, MultiplexTransportConfig config) {
        super(listener, config);
        this.transportConfig = config;
        if (config != null) {
            contextWeakReference = new WeakReference<>(config.getContext());
            this.requiresAudioSupport = Boolean.TRUE.equals(config.requiresAudioSupport()); //handle null case

        }
        this.sessionListener = listener;

    }

    public SdlSession(ISdlConnectionListener listener, TCPTransportConfig config) {
        super(listener, config);
        this.transportConfig = config;
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
        return !requiresAudioSupport || mediaStreamingStatus.isAudioOutputAvailable();

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
     * *****************************************************************  IProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

    @Override
    public void onProtocolMessageBytesToSend(SdlPacket packet) {
        sdlProtocol.sendPacket(packet);
    }

    @Override
    public void onProtocolSessionStarted(SessionType sessionType,
                                         byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {

        DebugTool.logInfo(TAG, "Protocol session started");

        this.sessionId = sessionID;
        if (sessionType.eq(SessionType.RPC)) {
            sessionHashId = hashID;
        }
        if (isEncrypted)
            encryptedServices.addIfAbsent(sessionType);
        this.sessionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
        if (serviceListeners != null && serviceListeners.containsKey(sessionType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceStarted(this, sessionType, isEncrypted);
            }
        }

    }

    public void onProtocolSessionStartedNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        onProtocolSessionNACKed(sessionType, sessionID, version, correlationID, rejectedParams);
    }

    @Override
    public void onProtocolSessionNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        this.sessionListener.onProtocolSessionStartedNACKed(sessionType,
                sessionID, version, correlationID, rejectedParams);
        if (serviceListeners != null && serviceListeners.containsKey(sessionType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            if (listeners != null) {
                for (ISdlServiceListener listener : listeners) {
                    listener.onServiceError(this, sessionType, "Start " + sessionType.toString() + " Service NAKed");
                }
            }
        }
    }

    @Override
    public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
                                       String correlationID) {
        this.sessionListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
        if (serviceListeners != null && serviceListeners.containsKey(sessionType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceEnded(this, sessionType);
            }
        }
        encryptedServices.remove(sessionType);
    }

    @Override
    public void onProtocolSessionEndedNACKed(SessionType sessionType,
                                             byte sessionID, String correlationID) {
        this.sessionListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
        if (serviceListeners != null && serviceListeners.containsKey(sessionType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for (ISdlServiceListener listener : listeners) {
                listener.onServiceError(this, sessionType, "End " + sessionType.toString() + " Service NACK'ed");
            }
        }
    }

    /* Not supported methods from IProtocolListener */
    @Override
    public void onHeartbeatTimedOut(byte sessionId) { /* Not supported */}

    @Override
    public void onAuthTokenReceived(String authToken, byte sessionID) {/* Do nothing */ }

    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  Fix after initial refactor *********************************************************************************
     *************************************************************************************************************************************************************************/
    //FIXME there is a lot of spaghetti code here that needs to be addressed. For first refactor the
    // the goal is to only refactor SdlSession. Another PR should be opened to fix all the packetizer
    // classes and method calls.

    //FIXME Move this logic to the related streaming manager
    private AbstractPacketizer videoPacketizer;
    private StreamPacketizer audioPacketizer;

    IStreamListener streamListener = new IStreamListener() {
        @Override
        public void sendStreamPacket(ProtocolMessage pm) {
            sendMessage(pm);
        }
    };

    private VideoStreamingProtocol getAcceptedProtocol() {
        if (acceptedVideoParams != null) {
            VideoStreamingFormat format = acceptedVideoParams.getFormat();
            if (format != null && format.getProtocol() != null) {
                return format.getProtocol();
            }
        }
        //Returns default protocol if none are found
        return new VideoStreamingParameters().getFormat().getProtocol();

    }

    public IVideoStreamListener startVideoStream() {
        VideoStreamingProtocol protocol = getAcceptedProtocol();
        try {
            switch (protocol) {
                case RAW: {
                    videoPacketizer = new StreamPacketizer(streamListener, null, SessionType.NAV, this.sessionId, this);
                    videoPacketizer.start();
                    return (IVideoStreamListener) videoPacketizer;
                }
                case RTP: {
                    //FIXME why is this not an extension of StreamPacketizer?
                    videoPacketizer = new RTPH264Packetizer(streamListener, SessionType.NAV, this.sessionId, this);
                    videoPacketizer.start();
                    return (IVideoStreamListener) videoPacketizer;
                }
                default:
                    DebugTool.logError(TAG, "Protocol " + protocol + " is not supported.");
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public IAudioStreamListener startAudioStream() {
        try {
            audioPacketizer = new StreamPacketizer(streamListener, null, SessionType.PCM, this.sessionId, this);
            audioPacketizer.start();
            return audioPacketizer;
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public void stopStream(SessionType serviceType) {
        if (SessionType.NAV.equals(serviceType)) {
            stopVideoStream();
        } else if (SessionType.PCM.equals(serviceType)) {
            stopAudioStream();
        }
        // Notify any listeners of the service being ended
        if (serviceListeners != null && serviceListeners.containsKey(serviceType)) {
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(serviceType);
            if (listeners != null && listeners.size() > 0) {
                for (ISdlServiceListener listener : listeners) {
                    listener.onServiceEnded(this, serviceType);
                }
            }
        }
    }


    public boolean stopVideoStream() {
        if (videoPacketizer != null) {
            videoPacketizer.stop();
            return true;
        }
        return false;
    }

    public boolean stopAudioStream() {
        if (audioPacketizer != null) {
            audioPacketizer.stop();
            return true;
        }
        return false;
    }

}
