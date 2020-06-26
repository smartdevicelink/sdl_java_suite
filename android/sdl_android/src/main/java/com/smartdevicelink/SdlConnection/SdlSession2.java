/*
 * Copyright (c) 2018 Livio, Inc.
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
import com.smartdevicelink.protocol.ISdlProtocol;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitor;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.MediaStreamingStatus;
import com.smartdevicelink.util.Version;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings({"WeakerAccess", "deprecation"})
public class SdlSession2 extends SdlSession implements ISdlProtocol{
    private static final String TAG = "SdlSession2";


    final protected SdlProtocol sdlProtocol;
    WeakReference<Context> contextWeakReference;
    MediaStreamingStatus mediaStreamingStatus;
    boolean requiresAudioSupport = false;

    @SuppressWarnings("SameReturnValue")
    @Deprecated
    public static SdlSession2 createSession(byte protocolVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {
        return null;
    }

    public SdlSession2(ISdlConnectionListener listener, MultiplexTransportConfig config){
        this.transportConfig = config;
        if(config != null){
            contextWeakReference = new WeakReference<>(config.getContext());
            this.requiresAudioSupport = Boolean.TRUE.equals(config.requiresAudioSupport()); //handle null case

        }
        this.sessionListener = listener;
        this.sdlProtocol = new SdlProtocol(this,config);

    }

    public SdlSession2(ISdlConnectionListener listener, TCPTransportConfig config){ //TODO is it better to have two constructors or make it take BaseTransportConfig?
        this.transportConfig = config;
        this.sessionListener = listener;
        this.sdlProtocol = new SdlProtocol(this,config);
    }

    boolean isAudioRequirementMet(){
        if(mediaStreamingStatus == null && contextWeakReference!= null && contextWeakReference.get() != null){
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

    @Deprecated
    @Override
    public SdlConnection getSdlConnection() {
        return null;
    }

    @Override
    public int getMtu(){
        if(this.sdlProtocol!=null){
            return this.sdlProtocol.getMtu();
        }else{
            return 0;
        }
    }

    @Override
    public long getMtu(SessionType type) {
        if (this.sdlProtocol != null) {
            return this.sdlProtocol.getMtu(type);
        } else {
            return 0;
        }
    }

    public void close() {
        if (sdlSecurity != null)
        {
            sdlSecurity.resetParams();
            sdlSecurity.shutDown();
        }
        if(sdlProtocol != null){
            sdlProtocol.endSession(sessionId, sessionHashId);
        }
    }

    @Override
    public void resetSession (){
        sdlProtocol.resetSession();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void startService (SessionType serviceType, byte sessionID, boolean isEncrypted) {
        if (isEncrypted){
            if (sdlSecurity != null){
                List<SessionType> serviceList = sdlSecurity.getServiceList();
                if (!serviceList.contains(serviceType))
                    serviceList.add(serviceType);

                if (!sdlSecurityInitializing) {
                    sdlSecurityInitializing = true;
                    sdlSecurity.initialize();
                    return;
                }
            }
        }
        sdlProtocol.startService(serviceType, sessionID, isEncrypted);
    }

    @Override
    public void endService (SessionType serviceType, byte sessionID) {
        if (sdlProtocol == null) {
            return;
        }
        sdlProtocol.endService(serviceType,sessionID);
    }


    public String getBroadcastComment(BaseTransportConfig myTransport) {
        return "Multiplexing";
    }


    @SuppressWarnings("RedundantThrows")
    @Override
    public void startSession() throws SdlException {
        if(!isAudioRequirementMet()){
            shutdown("Audio output not available");
            return;
        }

        sdlProtocol.start();
    }


    @Override
    public void sendMessage(ProtocolMessage msg) {
        if (sdlProtocol == null){
            return;
        }
        sdlProtocol.sendMessage(msg);
    }

    @Override
    public TransportType getCurrentTransportType() {
        return TransportType.MULTIPLEX;
    }

    @Override
    public boolean getIsConnected() {
        return sdlProtocol != null && sdlProtocol.isConnected();
    }


    public void shutdown(String info){
        DebugTool.logInfo(TAG, "Shutdown - " + info);
        if(mediaStreamingStatus != null) {
            mediaStreamingStatus.clear();
        }
        this.sessionListener.onTransportDisconnected(info);

    }

    @Override
    public void onTransportDisconnected(String info, boolean altTransportAvailable, BaseTransportConfig transportConfig) {
        this.sessionListener.onTransportDisconnected(info, altTransportAvailable, this.transportConfig);
    }

    /**
     * Get the current protocol version used by this session
     * @return Version that represents the Protocol version being used
     */
    @Override
    public Version getProtocolVersion(){
        if(sdlProtocol!=null){
            return sdlProtocol.getProtocolVersion();
        }
        return new Version(1,0,0);
    }


     /* ***********************************************************************************************************************************************************************
     * *****************************************************************  IProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

    @Override
    public void onProtocolMessageBytesToSend(SdlPacket packet) {
        //Log.d(TAG, "onProtocolMessageBytesToSend - " + packet.getTransportType());
        sdlProtocol.sendPacket(packet);
    }


    public void onProtocolSessionStartedNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams){
        onProtocolSessionNACKed(sessionType,sessionID,version,correlationID,rejectedParams);
    }

    @Override
    public void onProtocolSessionNACKed(SessionType sessionType, byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        this.sessionListener.onProtocolSessionStartedNACKed(sessionType,
                sessionID, version, correlationID, rejectedParams);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            if(listeners != null) {
                for (ISdlServiceListener listener : listeners) {
                    listener.onServiceError(this, sessionType, "Start " + sessionType.toString() + " Service NAKed");
                }
            }
        }
    }

    /* Not supported methods from IProtocolListener */
    @Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {/* Not supported */ }
    @Override
    public void heartbeatTimedOut(IHeartbeatMonitor monitor) {/* Not supported */}
    @Override
    public void onHeartbeatTimedOut(byte sessionId){ /* Not supported */}
    @Override
    public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) { /* Not supported */}
    @Override
    public void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {/* Not supported */}
    @Override
    public void onResetOutgoingHeartbeat(SessionType sessionType, byte sessionID) {/* Not supported */}
    @Override
    public void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID) {/* Not supported */}
    @Override
    public void onAuthTokenReceived(String authToken, byte sessionID){/* Do nothing */ }

    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  Security Listener  *********************************************************************************
     *************************************************************************************************************************************************************************/


    @Override
    public void onSecurityInitialized() {

        if (sdlProtocol != null && sdlSecurity != null)
        {
            List<SessionType> list = sdlSecurity.getServiceList();

            SessionType service;
            ListIterator<SessionType> iter = list.listIterator();

            while (iter.hasNext()) {
                service = iter.next();

                if (service != null)
                    sdlProtocol.startService(service, getSessionId(), true);

                iter.remove();
            }
        }
    }

    @Override
    public void stopStream(SessionType serviceType) {
        if(SessionType.NAV.equals(serviceType)){
            stopVideoStream();
        }else if(SessionType.PCM.equals(serviceType)){
            stopAudioStream();
        }
        // Notify any listeners of the service being ended
        if(serviceListeners != null && serviceListeners.containsKey(serviceType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(serviceType);
            if (listeners != null && listeners.size() > 0) {
                for (ISdlServiceListener listener : listeners) {
                    listener.onServiceEnded(this, serviceType);
                }
            }
        }
    }

    @Override
    public void onAuthTokenReceived(String authToken) {
        sessionListener.onAuthTokenReceived(authToken,sessionId);
    }

    /**
     * Check to see if a transport is available to start/use the supplied service.
     * @param sessionType the session that should be checked for transport availability
     * @return true if there is either a supported
     *         transport currently connected or a transport is
     *         available to connect with for the supplied service type.
     *         <br>false if there is no
     *         transport connected to support the service type in question and
     *          no possibility in the foreseeable future.
     */
    @Override
    public boolean isTransportForServiceAvailable(SessionType sessionType){
        return sdlProtocol!=null && sdlProtocol.isTransportForServiceAvailable(sessionType);
    }


    @Override
    @Deprecated
    public void clearConnection(){/* Not supported */}

    @SuppressWarnings("SameReturnValue")
    @Deprecated
    public static boolean removeConnection(SdlConnection connection){/* Not supported */ return false;}

    @Deprecated
    @Override
    public void checkForOpenMultiplexConnection(SdlConnection connection){/* Not supported */}


}
