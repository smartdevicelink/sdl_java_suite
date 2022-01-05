/*
 * Copyright (c) 2020 Livio, Inc.
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

import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.managers.lifecycle.RpcConverter;
import com.smartdevicelink.protocol.SecurityQueryPayload;
import com.smartdevicelink.protocol.ISdlProtocol;
import com.smartdevicelink.protocol.ISdlServiceListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
import com.smartdevicelink.protocol.enums.SecurityQueryErrorCode;
import com.smartdevicelink.protocol.enums.SecurityQueryID;
import com.smartdevicelink.protocol.enums.SecurityQueryType;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.security.ISecurityInitializedListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.transport.utl.TransportRecord;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.SystemInfo;
import com.smartdevicelink.util.Version;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public abstract class BaseSdlSession implements ISdlProtocol, ISecurityInitializedListener {

    private static final String TAG = "SdlSession";

    final protected SdlProtocolBase sdlProtocol;

    protected final BaseTransportConfig transportConfig;
    protected ISdlSessionListener sessionListener;
    protected SdlSecurityBase sdlSecurity = null;
    protected VideoStreamingParameters desiredVideoParams = null;
    protected VideoStreamingParameters acceptedVideoParams = null;

    protected int sessionId = -1;
    protected HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> serviceListeners;
    protected final CopyOnWriteArrayList<SessionType> encryptedServices = new CopyOnWriteArrayList<>();

    boolean sdlSecurityInitializing = false;

    public BaseSdlSession(ISdlSessionListener listener, BaseTransportConfig config) {
        this.transportConfig = config;
        this.sessionListener = listener;
        this.sdlProtocol = getSdlProtocolImplementation();

    }

    protected abstract SdlProtocolBase getSdlProtocolImplementation();

    public int getMtu() {
        if (this.sdlProtocol != null) {
            return this.sdlProtocol.getMtu();
        } else {
            return 0;
        }
    }

    public long getMtu(SessionType type) {
        if (this.sdlProtocol != null) {
            return this.sdlProtocol.getMtu(type);
        } else {
            return 0;
        }
    }

    public void close() {
        if (sdlSecurity != null) {
            sdlSecurity.resetParams();
            sdlSecurity.shutDown();
        }
        if (sdlProtocol != null) {
            sdlProtocol.endSession((byte) sessionId);
        }
    }


    public void startService(SessionType serviceType, boolean isEncrypted) {
        if (isEncrypted) {
            if (sdlSecurity != null) {
                List<SessionType> serviceList = sdlSecurity.getServiceList();
                if (!serviceList.contains(serviceType)) {
                    serviceList.add(serviceType);
                }

                if (!sdlSecurityInitializing) {
                    sdlSecurityInitializing = true;
                    sdlSecurity.initialize();
                    return;
                }
            }
        }
        sdlProtocol.startService(serviceType, (byte) this.sessionId, isEncrypted);
    }

    public void endService(SessionType serviceType) {
        if (sdlProtocol == null) {
            return;
        }
        sdlProtocol.endService(serviceType, (byte) this.sessionId);
    }


    public void startSession() throws SdlException {
        sdlProtocol.start();
    }


    public void sendMessage(ProtocolMessage msg) {
        if (sdlProtocol == null) {
            return;
        }
        sdlProtocol.sendMessage(msg);
    }

    public TransportType getCurrentTransportType() {
        return transportConfig.getTransportType();
    }

    public boolean getIsConnected() {
        return sdlProtocol != null && sdlProtocol.isConnected();
    }

    /**
     * Get the current protocol version used by this session
     *
     * @return Version that represents the Protocol version being used
     */
    public Version getProtocolVersion() {
        if (sdlProtocol != null) {
            return sdlProtocol.getProtocolVersion();
        }
        return new Version(1, 0, 0);
    }


    public BaseTransportConfig getTransportConfig() {
        return this.transportConfig;
    }

    public void setSdlSecurity(SdlSecurityBase sec) {
        sdlSecurity = sec;
    }


    protected void processControlService(ProtocolMessage msg) {
        if (sdlSecurity == null || msg.getData() == null)
            return;


        if (msg.getData().length < 12) {
            DebugTool.logError(TAG, "Security message is malformed, less than 12 bytes. It does not have a security payload header.");
        }
        // Check the client's message header for any internal errors
        // NOTE: Before Core v8.0.0, all these messages will be notifications. In Core v8.0.0 and later, received messages will have the proper query type. Therefore, we cannot do things based only on the query type being request or response.
        SecurityQueryPayload receivedHeader = SecurityQueryPayload.parseBinaryQueryHeader(msg.getData().clone());
        if (receivedHeader == null) {
            DebugTool.logError(TAG, "Module Security Query could not convert to object.");
            return;
        }

        int iLen = msg.getData().length - 12;
        byte[] data = new byte[iLen];
        System.arraycopy(msg.getData(), 12, data, 0, iLen);

        byte[] dataToRead = new byte[4096];

        Integer iNumBytes = null;

        // If the query is of type `Notification` and the id represents a client internal error, we abort the response message and the encryptionManager will not be in state ready.
        if (receivedHeader.getQueryID() == SecurityQueryID.SEND_INTERNAL_ERROR
                && receivedHeader.getQueryType() == SecurityQueryType.NOTIFICATION) {
            if (receivedHeader.getBulkData() != null && receivedHeader.getBulkDataSize() == 1) {
                DebugTool.logError(TAG, "Security Query module internal error: " + SecurityQueryErrorCode.valueOf(receivedHeader.getBulkData()[0]).getName());
            } else {
                DebugTool.logError(TAG, "Security Query module error: No information provided");
            }
            return;
        }

        if (receivedHeader.getQueryID() != SecurityQueryID.SEND_HANDSHAKE_DATA) {
            DebugTool.logError(TAG, "Security Query module error: Message is not a SEND_HANDSHAKE_DATA REQUEST");
            return;
        }

        if (receivedHeader.getQueryType() == SecurityQueryType.RESPONSE) {
            DebugTool.logError(TAG, "Security Query module error: Message is a response, which is not supported");
            return;
        }

        iNumBytes = sdlSecurity.runHandshake(data, dataToRead);

        ProtocolMessage protocolMessage;
        if (iNumBytes == null || iNumBytes <= 0) {
            DebugTool.logError(TAG, "Internal Error processing control service");
            protocolMessage = serverSecurityFailedMessageWithClientMessageHeader(msg.getCorrID());
        } else {
            protocolMessage = serverSecurityHandshakeMessageWithData(msg.getCorrID(), dataToRead);
        }

        //sdlSecurity.hs();

        sendMessage(protocolMessage);
    }

    private ProtocolMessage serverSecurityHandshakeMessageWithData(int correlationId, byte[] bulkData) {
        SecurityQueryPayload responseHeader = new SecurityQueryPayload();
        responseHeader.setQueryID(SecurityQueryID.SEND_HANDSHAKE_DATA);
        responseHeader.setQueryType(SecurityQueryType.RESPONSE);
        responseHeader.setCorrelationID(correlationId);
        responseHeader.setBulkData(bulkData);
        responseHeader.setJsonData(null);

        byte[] returnBytes = responseHeader.assembleBinaryData();

        ProtocolMessage protocolMessage = new ProtocolMessage();
        protocolMessage.setSessionType(SessionType.CONTROL);
        protocolMessage.setData(returnBytes);
        protocolMessage.setFunctionID(0x01);
        protocolMessage.setVersion((byte) sdlProtocol.getProtocolVersion().getMajor());
        protocolMessage.setSessionID((byte) this.sessionId);

        return protocolMessage;
    }

    private ProtocolMessage serverSecurityFailedMessageWithClientMessageHeader(int correlationId) {
        SecurityQueryPayload responseHeader = new SecurityQueryPayload();
        responseHeader.setQueryID(SecurityQueryID.SEND_INTERNAL_ERROR);
        responseHeader.setQueryType(SecurityQueryType.NOTIFICATION);
        responseHeader.setCorrelationID(correlationId);
        byte[] jsonData;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR.getValue());
            jsonObject.put("text", SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR.getName());
            jsonData = jsonObject.toString().getBytes();
        } catch (JSONException e) {
            DebugTool.logError(TAG, "JSON exception when constructing handshake error Notification");
            e.printStackTrace();
            jsonData = new byte[0];
        }
        responseHeader.setJsonData(jsonData);
        byte[] errorCode = new byte[1];
        errorCode[0] = SecurityQueryErrorCode.ERROR_UNKNOWN_INTERNAL_ERROR.getValue();
        responseHeader.setBulkData(errorCode);

        byte[] returnBytes = responseHeader.assembleBinaryData();

        ProtocolMessage protocolMessage = new ProtocolMessage();
        protocolMessage.setSessionType(SessionType.CONTROL);
        protocolMessage.setData(returnBytes);
        protocolMessage.setFunctionID(0x01);
        protocolMessage.setVersion((byte) sdlProtocol.getProtocolVersion().getMajor());
        protocolMessage.setSessionID((byte) this.sessionId);

        return protocolMessage;
    }

    /**
     * Extracts the SystemInfo out of a packet
     *
     * @param packet should be a StartServiceACK for the RPC service
     * @return an instance of SystemInfo if the information is available, null otherwise
     */
    protected SystemInfo extractSystemInfo(SdlPacket packet) {
        SystemInfo systemInfo = null;
        if (packet != null && packet.getFrameInfo() == SdlPacket.FRAME_INFO_START_SERVICE_ACK) {
            VehicleType vehicleType = null;

            String make = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.MAKE);
            String model = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.MODEL);
            String modelYear = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.MODEL_YEAR);
            String trim = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.TRIM);
            String softwareVersion = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.SYSTEM_SOFTWARE_VERSION);
            String hardwareVersion = (String) packet.getTag(ControlFrameTags.RPC.StartServiceACK.SYSTEM_HARDWARE_VERSION);

            if (make != null || model != null || modelYear != null || trim != null) {
                vehicleType = new VehicleType()
                        .setMake(make)
                        .setModel(model)
                        .setModelYear(modelYear)
                        .setTrim(trim);
            }

            if (vehicleType != null || softwareVersion != null || hardwareVersion != null) {
                systemInfo = new SystemInfo(vehicleType, softwareVersion, hardwareVersion);
            }
        }

        return systemInfo;
    }

    public boolean isServiceProtected(SessionType sType) {
        return encryptedServices.contains(sType);
    }

    public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
        if (serviceListeners == null) {
            serviceListeners = new HashMap<>();
        }
        if (serviceType != null && sdlServiceListener != null) {
            if (!serviceListeners.containsKey(serviceType)) {
                serviceListeners.put(serviceType, new CopyOnWriteArrayList<ISdlServiceListener>());
            }
            serviceListeners.get(serviceType).add(sdlServiceListener);
        }
    }

    public boolean removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener) {
        if (serviceListeners != null && serviceType != null && sdlServiceListener != null && serviceListeners.containsKey(serviceType)) {
            return serviceListeners.get(serviceType).remove(sdlServiceListener);
        }
        return false;
    }


    public HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> getServiceListeners() {
        return serviceListeners;
    }

    public void setDesiredVideoParams(VideoStreamingParameters params) {
        this.desiredVideoParams = params;
    }

    public VideoStreamingParameters getAcceptedVideoParams() {
        return acceptedVideoParams;
    }

    private VideoStreamingProtocol getAcceptedProtocol() {
        // acquire default protocol (RAW)
        VideoStreamingProtocol protocol = new VideoStreamingParameters().getFormat().getProtocol();

        if (acceptedVideoParams != null) {
            VideoStreamingFormat format = acceptedVideoParams.getFormat();
            if (format != null && format.getProtocol() != null) {
                protocol = format.getProtocol();
            }
        }

        return protocol;
    }




    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  ISdlProtocol Listener  ********************************************************************************
     *************************************************************************************************************************************************************************/

    @Override
    public void onProtocolMessageReceived(ProtocolMessage msg) {
        if (msg.getSessionType().equals(SessionType.CONTROL)) {
            processControlService(msg);
        } else if (SessionType.RPC.equals(msg.getSessionType())
                || SessionType.BULK_DATA.equals(msg.getSessionType())) {
            RPCMessage rpc = RpcConverter.extractRpc(msg, this.sdlProtocol.getProtocolVersion());
            this.sessionListener.onRPCMessageReceived(rpc);
        }

    }

    //To be implemented by child class
    @Override
    public abstract void onServiceStarted(SdlPacket packet, SessionType sessionType, int sessionID, Version version, boolean isEncrypted);

    @Override
    public abstract void onServiceEnded(SdlPacket packet, SessionType sessionType, int sessionID);

    @Override
    public abstract void onServiceError(SdlPacket packet, SessionType sessionType, int sessionID, String error);


    @Override
    public void onProtocolError(String info, Exception e) {
        DebugTool.logError(TAG, "on protocol error", e);
    }

    @Override
    public int getSessionId() {
        return this.sessionId;
    }

    @Override
    public void shutdown(String info) {
        DebugTool.logInfo(TAG, "Shutdown - " + info);
        this.sessionListener.onTransportDisconnected(info, false, this.transportConfig);
    }

    @Override
    public void onTransportDisconnected(String info, boolean altTransportAvailable, BaseTransportConfig transportConfig) {
        this.sessionListener.onTransportDisconnected(info, altTransportAvailable, this.transportConfig);
    }

    @Override
    public SdlSecurityBase getSdlSecurity() {
        return sdlSecurity;
    }

    /**
     * Returns the currently set desired video streaming parameters. If there haven't been any set,
     * the default options will be returned and set for this instance.
     *
     * @return the desired video streaming parameters
     */
    @Override
    public VideoStreamingParameters getDesiredVideoParams() {
        if (desiredVideoParams == null) {
            desiredVideoParams = new VideoStreamingParameters();
        }
        return desiredVideoParams;
    }

    @Override
    public void setAcceptedVideoParams(VideoStreamingParameters params) {
        this.acceptedVideoParams = params;
    }

    @Override
    public void onAuthTokenReceived(String authToken) {
        this.sessionListener.onAuthTokenReceived(authToken, sessionId);
    }

    /* ***********************************************************************************************************************************************************************
     * *****************************************************************  Security Listener  *********************************************************************************
     *************************************************************************************************************************************************************************/


    @Override
    public void onSecurityInitialized() {

        if (sdlProtocol != null && sdlSecurity != null) {
            List<SessionType> list = sdlSecurity.getServiceList();

            SessionType service;
            ListIterator<SessionType> iter = list.listIterator();

            while (iter.hasNext()) {
                service = iter.next();

                if (service != null)
                    sdlProtocol.startService(service, (byte) this.sessionId, true);

                iter.remove();
            }
        }
    }


    /**
     * Check to see if a transport is available to start/use the supplied service.
     *
     * @param sessionType the session that should be checked for transport availability
     * @return true if there is either a supported
     * transport currently connected or a transport is
     * available to connect with for the supplied service type.
     * <br>false if there is no
     * transport connected to support the service type in question and
     * no possibility in the foreseeable future.
     */
    public boolean isTransportForServiceAvailable(SessionType sessionType) {
        return sdlProtocol != null && sdlProtocol.isTransportForServiceAvailable(sessionType);
    }

    /**
     * Retrieves list of the active transports
     *
     * @return a list of active transports
     */
    @Nullable
    public List<TransportRecord> getActiveTransports() {
        if (this.sdlProtocol != null) {
            return this.sdlProtocol.getActiveTransports();
        } else {
            return null;
        }
    }
}
