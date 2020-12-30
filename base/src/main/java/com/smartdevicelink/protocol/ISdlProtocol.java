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

package com.smartdevicelink.protocol;


import androidx.annotation.RestrictTo;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.util.Version;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public interface ISdlProtocol {

    /**
     * Called to indicate that a complete message (RPC, BULK, etc.) has been received.
     *
     * @param msg the message that was received
     */
    void onProtocolMessageReceived(ProtocolMessage msg);

    /**
     * Called to indicate that a service has been started
     *
     * @param packet      the control packet StartServiceACK received from the connected device
     * @param serviceType the service type that has been started
     * @param sessionID   the session ID that this service has been started on
     * @param version     the protocol version used for this session and service
     * @param isEncrypted if the service is encrypted
     */
    void onServiceStarted(SdlPacket packet, SessionType serviceType, int sessionID, Version version, boolean isEncrypted);

    /**
     * This will get called when a service has ended
     *
     * @param packet      the packet received that ended this service
     * @param serviceType the service type that has ended
     * @param sessionID   the id of the session that this service was operating on
     */
    void onServiceEnded(SdlPacket packet, SessionType serviceType, int sessionID);

    /**
     * If there is an error with starting or stopping the service or any other error this method
     * will be called. This will also be called if the service was operating on a transport that
     * has been disconnected.
     *
     * @param packet      if there is a packet that caused this error it will be included, however this
     *                    can be null.
     * @param serviceType the service type that experienced the error
     * @param sessionID   the session ID that this service was associated with
     * @param error       a human readable string of the error
     */
    void onServiceError(SdlPacket packet, SessionType serviceType, int sessionID, String error);

    /**
     * Called to indicate that a protocol error was detected in received data.
     *
     * @param info a human readable string of the error
     * @param e    the exception if one occurred
     */
    void onProtocolError(String info, Exception e);

    /**
     * Method that the protocol layer will use to obtain the session ID
     *
     * @return the session ID associated with the protocol instance
     */
    int getSessionId();

    /**
     * A request made by the protocol layer to shutdown the layers above it. Likely due to
     * the RPC service being shutdown or the primary transport disconnecting.
     *
     * @param info human readable string on why the shutdown should occur
     */
    void shutdown(String info);

    /**
     * Called when a transport disconnects
     *
     * @param info                  a human readable string including information on the disconnected transport
     * @param altTransportAvailable a boolean flag indicating if there is another transport that can
     *                              be used to connect with the SDL enabled device.
     * @param transportConfig       the previously supplied transport config
     */
    void onTransportDisconnected(String info, boolean altTransportAvailable, BaseTransportConfig transportConfig);

    /**
     * A method that should be implemented by the hosting class of the SdlProtocol instance that will
     * return the currently being used security library if any.
     *
     * @return the security library to be used to encrypt/decrypt packets
     */
    SdlSecurityBase getSdlSecurity();

    /**
     * A method that should be implemented by the hosting class of the SdlProtocol instance that will
     * return the desired video streaming parameters. These parameters will be requested if
     * another component has requested the video streaming service to start.
     *
     * @return the developer supplied desired video streaming parameters
     */
    VideoStreamingParameters getDesiredVideoParams();

    /**
     * A callback that will be called when the video service has been successfully started and the
     * streaming parameters have been negotiated. This should be called prior to the video service
     * started callback.
     *
     * @param acceptedVideoParams the negotiated and accepted video parameters that should be used
     *                            to stream to the SDL enabled device.
     */
    void setAcceptedVideoParams(VideoStreamingParameters acceptedVideoParams);

    /**
     * A callback to indicate the SDL connected device has supplied an authentication token to this
     * application. It will be called after the service start callback.
     *
     * @param authToken
     */
    void onAuthTokenReceived(String authToken);

    boolean onVehicleTypeReceived(VehicleType vehicleType, String systemSoftwareVersion, String systemHardwareVersion);
}
