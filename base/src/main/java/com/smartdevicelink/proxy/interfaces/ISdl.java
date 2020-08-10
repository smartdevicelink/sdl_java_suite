package com.smartdevicelink.proxy.interfaces;

import androidx.annotation.NonNull;

import com.livio.taskmaster.Taskmaster;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCRequestListener;
import com.smartdevicelink.streaming.audio.AudioStreamingCodec;
import com.smartdevicelink.streaming.audio.AudioStreamingParams;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.util.Version;

import java.util.List;

/*
 * Copyright (c) 2017 Livio, Inc.
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
@SuppressWarnings("unused")
public interface ISdl {

    /**
     * Starts the connection with the module
     */
    void start();

    /**
     * Ends connection with the module
     */
    void stop();

    /**
     * Method to check if the session is connected
     * @return if there is a connected session
     */
    boolean isConnected();

    /**
     * Add a service listener for a specific service type
     * @param serviceType service type that the listener will be attached to
     * @param sdlServiceListener listener for events that happen to the service
     */
    void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener);

    /**
     * Remote a service listener for a specific service type
     * @param serviceType service type that the listener was attached to
     * @param sdlServiceListener service listener that was previously added for the service type
     */
    void removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener);

    /**
     * Starts the video streaming service
     * @param parameters desired video streaming params for this sevice to be started with
     * @param encrypted flag to start this service with encryption or not
     */
    void startVideoService(VideoStreamingParameters parameters, boolean encrypted);

    /**
     * Stops the video service if open
     */
    void stopVideoService();

    /**
     * Starts the video streaming service
     * @param isEncrypted flag to start this service with encryption or not
     * @param parameters desired video streaming params for this sevice to be started with
     */
    @Deprecated
    IVideoStreamListener startVideoStream(boolean isEncrypted, VideoStreamingParameters parameters);

    /**
     * Starts the Audio streaming service
     * @param encrypted flag to start this service with encryption or not
     */
    @Deprecated
    void startAudioService(boolean encrypted, AudioStreamingCodec codec, AudioStreamingParams params);

    /**
     * Starts the Audio streaming service
     * @param encrypted flag to start this service with encryption or not
     */
    void startAudioService(boolean encrypted);

    /**
     * Stops the audio service if open
     */
    void stopAudioService();

    /**
     * Start Audio Stream and return IAudioStreamListener
     * @param isEncrypted whether or not the audio stream should be encrypted
     * @param codec the codec that should be used for the audio stream
     * @param params specific options and settings associated with the audio stream
     * @return IAudioStreamListener, an interface that allows the writing of audio data
     */
    @Deprecated
    IAudioStreamListener startAudioStream(boolean isEncrypted, AudioStreamingCodec codec, AudioStreamingParams params);

    /**
     * Pass an RPC message through the proxy to be sent to the connected module
     * @param message RPCRequest that should be sent to the module
     */
    @Deprecated
    void sendRPCRequest(RPCRequest message);

    /**
     * Pass an RPC message through the proxy to be sent to the connected module
     * @param message RPCMessage that should be sent to the module
     */
    void sendRPC(RPCMessage message);

    /**
     * Pass a list of RPC requests through the proxy to be sent to core
     * @param rpcs List of RPC requests
     * @param listener OnMultipleRequestListener that is called between requests and after all are processed
     */
    @Deprecated
    void sendRequests(List<? extends RPCRequest> rpcs, final OnMultipleRequestListener listener);

    /**
     * Pass a list of RPC messages through the proxy to be sent to core
     * @param rpcs List of RPC messages
     * @param listener OnMultipleRequestListener that is called between requests and after all are processed
     */
    void sendRPCs(List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener);

    /**
     * Takes a list of RPCMessages and sends it to SDL in a synchronous fashion. Responses are captured through callback on OnMultipleRequestListener.
     * For sending requests asynchronously, use sendRequests <br>
     *
     * <strong>NOTE: This will override any listeners on individual RPCs</strong><br>
     *
     * <strong>ADDITIONAL NOTE: This only takes the type of RPCRequest for now, notifications and responses will be thrown out</strong>
     *
     * @param rpcs is the list of RPCMessages being sent
     * @param listener listener for updates and completions
     */
    void sendSequentialRPCs(final List<? extends RPCMessage> rpcs, final OnMultipleRequestListener listener);

        /**
         * Add an OnRPCNotificationListener for specified notification
         * @param notificationId FunctionID of the notification that is to be listened for
         * @param listener listener that should be added for the notification ID
         */
    void addOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener);

    /**
     * Removes an OnRPCNotificationListener for specified notification
     * @param notificationId FunctionID of the notification that was to be listened for
     * @param listener listener that was previously added for the notification ID
     */
    boolean removeOnRPCNotificationListener(FunctionID notificationId, OnRPCNotificationListener listener);

    /**
     * Add an OnRPCRequestListener for specified request
     * @param functionID FunctionID of the request that is to be listened for
     * @param listener listener that should be added for the request ID
     */
    void addOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener);

    /**
     * Removes an OnRPCRequestListener for specified request
     * @param functionID FunctionID of the request that was to be listened for
     * @param listener listener that was previously added for the request ID
     */
    boolean removeOnRPCRequestListener(FunctionID functionID, OnRPCRequestListener listener);

    /**
     * Add an OnRPCResponseListener for specified response
     * @param responseId FunctionID of the response that is to be listened for
     * @param listener listener that should be added for the response ID
     */
    void addOnRPCListener(FunctionID responseId, OnRPCListener listener);

    /**
     * Removes an OnRPCResponseListener for specified response
     * @param responseId FunctionID of the response that was to be listened for
     * @param listener listener that was previously added for the response ID
     */
    boolean removeOnRPCListener(FunctionID responseId, OnRPCListener listener);

    /**
     * Get SystemCapability Object
     * @param systemCapabilityType a system capability type that should be retrieved
     * @return the system capability provided if available, null if not
     * @deprecated use {@link #getCapability(SystemCapabilityType, OnSystemCapabilityListener, boolean)} instead.
     */
    @Deprecated
    Object getCapability(SystemCapabilityType systemCapabilityType);

    /**
     * Get Capability
     * @param systemCapabilityType a system capability type that should be retrieved
     * @param scListener listener that will be called when the system capability is retrieved. If already cached, it
     *                   will be called immediately
     * @deprecated use {@link #getCapability(SystemCapabilityType, OnSystemCapabilityListener, boolean)} instead.
     */
    @Deprecated
    void getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener);

    /** Gets the capability object that corresponds to the supplied capability type by returning the currently cached value immediately (or null) as well as calling the listener immediately with the cached value, if available. If not available, the listener will retrieve a new value and return that when the head unit responds.
     * <strong>If capability is not cached, the method will return null and trigger the supplied listener when the capability becomes available</strong>
     * @param systemCapabilityType type of capability desired
     * @param scListener callback to execute upon retrieving capability
     * @param forceUpdate flag to force getting a new fresh copy of the capability from the head unit even if it is cached
     * @return desired capability if it is cached in the manager, otherwise returns a null object
     */
    @Deprecated
    Object getCapability(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener scListener, boolean forceUpdate);

    /**
     * Get RegisterAppInterfaceResponse
     * @return the RegisterAppInterfaceResponse if available, null if not
     */
    RegisterAppInterfaceResponse getRegisterAppInterfaceResponse();

    /**
     * Check if capability is supported
     * @param systemCapabilityType a system capability type that should be checked for support
     * @return Boolean whether or not the supplied capability type is supported on the connected module
     */
    @Deprecated
    boolean isCapabilitySupported(SystemCapabilityType systemCapabilityType);

    /**
     * Add a listener to be called whenever a new capability is retrieved
     * @param systemCapabilityType Type of capability desired
     * @param listener callback to execute upon retrieving capability
     */
    @Deprecated
    void addOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener);

    /**
     * Remove an OnSystemCapabilityListener that was previously added
     * @param systemCapabilityType Type of capability
     * @param listener the listener that should be removed
     */
    @Deprecated
    boolean removeOnSystemCapabilityListener(SystemCapabilityType systemCapabilityType, OnSystemCapabilityListener listener);

    /**
     * Check to see if a transport is available to start/use the supplied service.
     * @param serviceType the session that should be checked for transport availability
     * @return true if there is either a supported
     *         transport currently connected or a transport is
     *         available to connect with for the supplied service type.
     *         <br>false if there is no
     *         transport connected to support the service type in question and
     *          no possibility in the foreseeable future.
     */
    boolean isTransportForServiceAvailable(SessionType serviceType);

    /**
     * Get the RPC specification version currently being used for the SDL messages
     * @return SdlMsgVersion the current RPC specification version
     */
    @NonNull SdlMsgVersion getSdlMsgVersion();

    /**
     * Get the protocol version of this session
     * @return byte value representing WiPro version
     */
    @NonNull Version getProtocolVersion();

    /**
     * Start encrypted RPC service
     */
    void startRPCEncryption();

    Taskmaster getTaskmaster();
}
