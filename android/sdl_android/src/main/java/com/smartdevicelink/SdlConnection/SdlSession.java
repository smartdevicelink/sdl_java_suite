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
package com.smartdevicelink.SdlConnection;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.Surface;

import com.smartdevicelink.encoder.SdlEncoder;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitor;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitorListener;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.interfaces.IAudioStreamListener;
import com.smartdevicelink.proxy.interfaces.ISdlServiceListener;
import com.smartdevicelink.proxy.interfaces.IVideoStreamListener;
import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.security.ISecurityInitializedListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransport;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

@Deprecated
public class SdlSession implements ISdlConnectionListener, IHeartbeatMonitorListener, IStreamListener, ISecurityInitializedListener {

   private static final String TAG = "SdlSession";

    protected final static int BUFF_READ_SIZE = 1024;

	private static CopyOnWriteArrayList<SdlConnection> shareConnections = new CopyOnWriteArrayList<SdlConnection>();

	private byte wiproProcolVer;

    protected BaseTransportConfig transportConfig;
    protected ISdlConnectionListener sessionListener;
	protected LockScreenManager lockScreenMan  = new LockScreenManager();
	protected SdlSecurityBase sdlSecurity = null;
	protected VideoStreamingParameters desiredVideoParams = null;
	protected VideoStreamingParameters acceptedVideoParams = null;

	protected byte sessionId;
	protected int sessionHashId = 0;
	protected HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> serviceListeners;
	protected CopyOnWriteArrayList<SessionType> encryptedServices = new CopyOnWriteArrayList<SessionType>();


	SdlConnection _sdlConnection = null;

	IHeartbeatMonitor _outgoingHeartbeatMonitor = null;
	IHeartbeatMonitor _incomingHeartbeatMonitor = null;

    StreamRPCPacketizer mRPCPacketizer = null;
    AbstractPacketizer mVideoPacketizer = null;
    StreamPacketizer mAudioPacketizer = null;
    SdlEncoder mSdlEncoder = null;
    VirtualDisplayEncoder virtualDisplayEncoder = null;
    boolean sdlSecurityInitializing = false;

    public static SdlSession createSession(byte wiproVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {

        SdlSession session =  new SdlSession();
        session.wiproProcolVer = wiproVersion;
        session.sessionListener = listener;
        session.transportConfig = btConfig;

        return session;
    }

    public BaseTransportConfig getTransportConfig() {
        return this.transportConfig;
    }

    public LockScreenManager getLockScreenMan() {
        return lockScreenMan;
    }


    public IHeartbeatMonitor getOutgoingHeartbeatMonitor() {
        return _outgoingHeartbeatMonitor;
    }

    public IHeartbeatMonitor getIncomingHeartbeatMonitor() {
        return _incomingHeartbeatMonitor;
    }

    public void setOutgoingHeartbeatMonitor(IHeartbeatMonitor outgoingHeartbeatMonitor) {
        this._outgoingHeartbeatMonitor = outgoingHeartbeatMonitor;
        _outgoingHeartbeatMonitor.setListener(this);
    }

    public void setIncomingHeartbeatMonitor(IHeartbeatMonitor incomingHeartbeatMonitor) {
        this._incomingHeartbeatMonitor = incomingHeartbeatMonitor;
        _incomingHeartbeatMonitor.setListener(this);
    }

    public int getSessionHashId() {
        return this.sessionHashId;
    }

    public byte getSessionId() {
        return this.sessionId;
    }

    public SdlConnection getSdlConnection() {
        return this._sdlConnection;
    }

    public int getMtu(){
        if(this._sdlConnection!=null){
            return this._sdlConnection.getWiProProtocol().getMtu();
        }else{
            return 0;
        }
    }

    public long getMtu(SessionType type) {
        if (this._sdlConnection != null) {
            return this._sdlConnection.getWiProProtocol().getMtu(type);
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

        if (_sdlConnection != null) { //sessionId == 0 means session is not started.
            //_sdlConnection.unregisterSession(this);

            if (_sdlConnection.getRegisterCount() == 0) {
                shareConnections.remove(_sdlConnection);
            }

            _sdlConnection = null;
        }
    }

    public void resetSession(){

    }

    public void startStream(InputStream is, SessionType sType, byte rpcSessionID) throws IOException {
        if (sType.equals(SessionType.NAV))
        {
            // protocol is fixed to RAW
            StreamPacketizer packetizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
            packetizer.sdlConnection = this.getSdlConnection();
            mVideoPacketizer = packetizer;
            mVideoPacketizer.start();
        }
        else if (sType.equals(SessionType.PCM))
        {
            mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
            mAudioPacketizer.sdlConnection = this.getSdlConnection();
            mAudioPacketizer.start();
        }
    }

    @SuppressLint("NewApi")
    public OutputStream startStream(SessionType sType, byte rpcSessionID) throws IOException {
        OutputStream os = new PipedOutputStream();
        InputStream is = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            is = new PipedInputStream((PipedOutputStream) os, BUFF_READ_SIZE);
        } else {
            is = new PipedInputStream((PipedOutputStream) os);
        }
        if (sType.equals(SessionType.NAV))
        {
            // protocol is fixed to RAW
            StreamPacketizer packetizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
            packetizer.sdlConnection = this.getSdlConnection();
            mVideoPacketizer = packetizer;
            mVideoPacketizer.start();
        }
        else if (sType.equals(SessionType.PCM))
        {
            mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
            mAudioPacketizer.sdlConnection = this.getSdlConnection();
            mAudioPacketizer.start();
        }
        else
        {
            os.close();
            is.close();
            return null;
        }
        return os;
    }

    public IVideoStreamListener startVideoStream() {
        byte rpcSessionID = getSessionId();
        VideoStreamingProtocol protocol = getAcceptedProtocol();
        try {
            switch (protocol) {
                case RAW: {
                    StreamPacketizer packetizer = new StreamPacketizer(this, null, SessionType.NAV, rpcSessionID, this);
                    packetizer.sdlConnection = this.getSdlConnection();
                    mVideoPacketizer = packetizer;
                    mVideoPacketizer.start();
                    return packetizer;
                }
                case RTP: {
                    RTPH264Packetizer packetizer = new RTPH264Packetizer(this, SessionType.NAV, rpcSessionID, this);
                    mVideoPacketizer = packetizer;
                    mVideoPacketizer.start();
                    return packetizer;
                }
                default:
                    DebugTool.logError("Protocol " + protocol + " is not supported.");
                    return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public IAudioStreamListener startAudioStream() {
        byte rpcSessionID = getSessionId();
        try {
            StreamPacketizer packetizer = new StreamPacketizer(this, null, SessionType.PCM, rpcSessionID, this);
            packetizer.sdlConnection = this.getSdlConnection();
            mAudioPacketizer = packetizer;
            mAudioPacketizer.start();
            return packetizer;
        } catch (IOException e) {
            return null;
        }
    }

    @Deprecated
    public void startRPCStream(InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
        try {
            mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0, this);
            mRPCPacketizer.start();
        } catch (Exception e) {
            DebugTool.logError("Unable to start streaming:" + e.toString());
        }
    }

    @Deprecated
    public OutputStream startRPCStream(RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
        try {
            OutputStream os = new PipedOutputStream();
            InputStream is = new PipedInputStream((PipedOutputStream) os);
            mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0, this);
            mRPCPacketizer.start();
            return os;
        } catch (Exception e) {
            DebugTool.logError("Unable to start streaming:" + e.toString());
        }
        return null;
    }

    @Deprecated
    public void pauseRPCStream()
    {
        if (mRPCPacketizer != null)
        {
            mRPCPacketizer.pause();
        }
    }

    @Deprecated
    public void resumeRPCStream()
    {
        if (mRPCPacketizer != null)
        {
            mRPCPacketizer.resume();
        }
    }

    @Deprecated
    public void stopRPCStream()
    {
        if (mRPCPacketizer != null)
        {
            mRPCPacketizer.stop();
        }
    }

    public boolean stopAudioStream()
    {
        if (mAudioPacketizer != null)
        {
            mAudioPacketizer.stop();
            return true;
        }
        return false;
    }

    public boolean stopVideoStream()
    {
        if (mVideoPacketizer != null)
        {
            mVideoPacketizer.stop();
            return true;
        }
        return false;
    }

    public boolean pauseAudioStream()
    {
        if (mAudioPacketizer != null)
        {
            mAudioPacketizer.pause();
            return true;
        }
        return false;
    }

    public boolean pauseVideoStream()
    {
        if (mVideoPacketizer != null)
        {
            mVideoPacketizer.pause();
            return true;
        }
        return false;
    }

    public boolean resumeAudioStream()
    {
        if (mAudioPacketizer != null)
        {
            mAudioPacketizer.resume();
            return true;
        }
        return false;
    }

    public boolean resumeVideoStream()
    {
        if (mVideoPacketizer != null)
        {
            mVideoPacketizer.resume();
            return true;
        }
        return false;
    }

    public Surface createOpenGLInputSurface(int frameRate, int iFrameInterval, int width,
                                            int height, int bitrate, SessionType sType, byte rpcSessionID) {
        IVideoStreamListener encoderListener = startVideoStream();
        if (encoderListener == null) {
            return null;
        }

        mSdlEncoder = new SdlEncoder();
        mSdlEncoder.setFrameRate(frameRate);
        mSdlEncoder.setFrameInterval(iFrameInterval);
        mSdlEncoder.setFrameWidth(width);
        mSdlEncoder.setFrameHeight(height);
        mSdlEncoder.setBitrate(bitrate);
        mSdlEncoder.setOutputListener(encoderListener);
        return mSdlEncoder.prepareEncoder();
    }

    public void startEncoder () {
        if(mSdlEncoder != null) {
            mSdlEncoder.startEncoder();
        }
    }

    public void releaseEncoder() {
        if(mSdlEncoder != null) {
            mSdlEncoder.releaseEncoder();
        }
    }

    public void drainEncoder(boolean endOfStream) {
        if(mSdlEncoder != null) {
            mSdlEncoder.drainEncoder(endOfStream);
        }
    }

    @Override
    public void sendStreamPacket(ProtocolMessage pm) {
        sendMessage(pm);
    }

    public void setSdlSecurity(SdlSecurityBase sec) {
        sdlSecurity = sec;
    }

    public SdlSecurityBase getSdlSecurity() {
        return sdlSecurity;
    }

    public void startService (SessionType serviceType, byte sessionID, boolean isEncrypted) {
        if (_sdlConnection == null)
            return;

        if (isEncrypted)
        {
            if (sdlSecurity != null)
            {
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
        _sdlConnection.startService(serviceType, sessionID, isEncrypted);
    }

    public void endService (SessionType serviceType, byte sessionID) {
        if (_sdlConnection == null)
            return;
        _sdlConnection.endService(serviceType, sessionID);
    }

    protected void processControlService(ProtocolMessage msg) {
        if (sdlSecurity == null)
            return;
        int ilen = msg.getData().length - 12;
        byte[] data = new byte[ilen];
        System.arraycopy(msg.getData(), 12, data, 0, ilen);

        byte[] dataToRead = new byte[4096];

        Integer iNumBytes = sdlSecurity.runHandshake(data, dataToRead);

        if (iNumBytes == null || iNumBytes <= 0)
            return;

        byte[] returnBytes = new byte[iNumBytes];
        System.arraycopy(dataToRead, 0, returnBytes, 0, iNumBytes);
        ProtocolMessage protocolMessage = new ProtocolMessage();
        protocolMessage.setSessionType(SessionType.CONTROL);
        protocolMessage.setData(returnBytes);
        protocolMessage.setFunctionID(0x01);
        protocolMessage.setVersion(wiproProcolVer);
        protocolMessage.setSessionID(getSessionId());

        //sdlSecurity.hs();

        sendMessage(protocolMessage);
    }

    public String getBroadcastComment(BaseTransportConfig myTransport) {
        SdlConnection connection = null;
        if (myTransport.shareConnection()) {
            connection = findTheProperConnection(myTransport);
        } else {
            connection = this._sdlConnection;
        }

        if (connection != null)
            return connection.getBroadcastComment();

        return "";
    }


    public void startSession() throws SdlException {
        SdlConnection connection = null;
        if (this.transportConfig.shareConnection()) {
            connection = findTheProperConnection(this.transportConfig);

            if (connection == null) {
                connection = new SdlConnection(this.transportConfig);
                shareConnections.add(connection);
            }
        } else {
            connection = new SdlConnection(this.transportConfig);
        }

        this._sdlConnection = connection;
        connection.registerSession(this); //Handshake will start when register.
    }

    protected void initialiseSession() {
        if (_outgoingHeartbeatMonitor != null) {
            _outgoingHeartbeatMonitor.start();
        }
        if (_incomingHeartbeatMonitor != null) {
            _incomingHeartbeatMonitor.start();
        }
    }

    public void sendMessage(ProtocolMessage msg) {
        if (_sdlConnection == null)
            return;
        _sdlConnection.sendMessage(msg);
    }

    public TransportType getCurrentTransportType() {
        if (_sdlConnection == null)
            return null;
        return _sdlConnection.getCurrentTransportType();
    }

    public boolean getIsConnected() {
        if (_sdlConnection == null)
            return false;
        return _sdlConnection != null && _sdlConnection.getIsConnected();
    }

    public boolean isServiceProtected(SessionType sType) {
        return encryptedServices.contains(sType);
    }

    @Override
    public void onTransportDisconnected(String info) {
        this.sessionListener.onTransportDisconnected(info);
    }

    @Override
    public void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig) {
        this.sessionListener.onTransportDisconnected(info);
    }

    @Override
    public void onTransportError(String info, Exception e) {
        this.sessionListener.onTransportError(info, e);
    }

    @Override
    public void onProtocolMessageReceived(ProtocolMessage msg) {
        if (msg.getSessionType().equals(SessionType.CONTROL)) {
            processControlService(msg);
            return;
        }

        this.sessionListener.onProtocolMessageReceived(msg);
    }

    @Override
    public void onHeartbeatTimedOut(byte sessionID) {
        this.sessionListener.onHeartbeatTimedOut(sessionID);

    }


    @Override
    public void onProtocolSessionStarted(SessionType sessionType,
                                         byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {
        this.sessionId = sessionID;
        lockScreenMan.setSessionID(sessionID);
        if (sessionType.eq(SessionType.RPC)){
            sessionHashId = hashID;
            wiproProcolVer = version;
        }
        if (isEncrypted)
            encryptedServices.addIfAbsent(sessionType);
        this.sessionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceStarted(this, sessionType, isEncrypted);
            }
        }
        //if (version == 3)
        initialiseSession();

    }

    @Override
    public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
                                       String correlationID) {
        this.sessionListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceEnded(this, sessionType);
            }
        }
        encryptedServices.remove(sessionType);
    }

    @Override
    public void onProtocolError(String info, Exception e) {
        this.sessionListener.onProtocolError(info, e);
    }

    @Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {
        DebugTool.logInfo(TAG, "Asked to send heartbeat");
        if (_sdlConnection != null)
            _sdlConnection.sendHeartbeat(this);
    }

    @Override
    public void heartbeatTimedOut(IHeartbeatMonitor monitor) {
        if (_sdlConnection != null)
            _sdlConnection._connectionListener.onHeartbeatTimedOut(this.sessionId);
        close();
    }

    private static SdlConnection findTheProperConnection(BaseTransportConfig config) {
        SdlConnection connection = null;

        int minCount = 0;
        for (SdlConnection c : shareConnections) {
            if (c.getCurrentTransportType() == config.getTransportType()) {
                if (minCount == 0 || minCount >= c.getRegisterCount()) {
                    connection = c;
                    minCount = c.getRegisterCount();
                }
            }
        }

        return connection;
    }

    @Override
    public void onProtocolSessionStartedNACKed(SessionType sessionType,
                                               byte sessionID, byte version, String correlationID, List<String> rejectedParams) {
        this.sessionListener.onProtocolSessionStartedNACKed(sessionType,
                sessionID, version, correlationID, rejectedParams);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceError(this, sessionType, "Start "+ sessionType.toString() +" Service NACK'ed");
            }
        }
    }

    @Override
    public void onProtocolSessionEndedNACKed(SessionType sessionType,
                                             byte sessionID, String correlationID) {
        this.sessionListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
        if(serviceListeners != null && serviceListeners.containsKey(sessionType)){
            CopyOnWriteArrayList<ISdlServiceListener> listeners = serviceListeners.get(sessionType);
            for(ISdlServiceListener listener:listeners){
                listener.onServiceError(this, sessionType, "End "+ sessionType.toString() +" Service NACK'ed");
            }
        }
    }

    @Override
    public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID) {
        this.sessionListener.onProtocolServiceDataACK(sessionType, dataSize, sessionID);
    }

    @Override
    public void onAuthTokenReceived(String authToken, byte sessionId) {
        this.sessionListener.onAuthTokenReceived(authToken, sessionId);
    }

    @Override
    public void onSecurityInitialized() {

        if (_sdlConnection != null && sdlSecurity != null)
        {
            List<SessionType> list = sdlSecurity.getServiceList();

            SessionType service;
            ListIterator<SessionType> iter = list.listIterator();

            while (iter.hasNext()) {
                service = iter.next();

                if (service != null)
                    _sdlConnection.startService(service, getSessionId(), true);

                iter.remove();
            }
        }
    }

    public void clearConnection(){
        _sdlConnection = null;
    }

    public void checkForOpenMultiplexConnection(SdlConnection connection){
        removeConnection(connection);
        connection.unregisterSession(this);
        _sdlConnection = null;
        for (SdlConnection c : shareConnections) {
            if (c.getCurrentTransportType() == TransportType.MULTIPLEX) {
                if(c.getIsConnected() || ((MultiplexTransport)c._transport).isPendingConnected()){
                    _sdlConnection = c;
                    try {
                        _sdlConnection.registerSession(this);//Handshake will start when register.
                    } catch (SdlException e) {
                        e.printStackTrace();
                    }
                    return;
                }

            }
        }
    }
    public static boolean removeConnection(SdlConnection connection){
        return shareConnections.remove(connection);
    }

    public void addServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener){
        if(serviceListeners == null){
            serviceListeners = new HashMap<>();
        }
        if(serviceType != null && sdlServiceListener != null){
            if(!serviceListeners.containsKey(serviceType)){
                serviceListeners.put(serviceType,new CopyOnWriteArrayList<ISdlServiceListener>());
            }
            serviceListeners.get(serviceType).add(sdlServiceListener);
        }
    }

    public boolean removeServiceListener(SessionType serviceType, ISdlServiceListener sdlServiceListener){
        if(serviceListeners!= null && serviceType != null && sdlServiceListener != null && serviceListeners.containsKey(serviceType)){
            return serviceListeners.get(serviceType).remove(sdlServiceListener);
        }
        return false;
    }


    public HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> getServiceListeners(){
        return serviceListeners;
    }

    public void setDesiredVideoParams(VideoStreamingParameters params){
        this.desiredVideoParams = params;
    }

    /**
     * Returns the currently set desired video streaming parameters. If there haven't been any set,
     * the default options will be returned and set for this instance.
     * @return
     */
    public VideoStreamingParameters getDesiredVideoParams(){
        if(desiredVideoParams == null){
            desiredVideoParams = new VideoStreamingParameters();
        }
        return desiredVideoParams;
    }

    public void setAcceptedVideoParams(VideoStreamingParameters params){
        this.acceptedVideoParams = params;
    }

    public VideoStreamingParameters getAcceptedVideoParams(){
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

    public Version getProtocolVersion(){
        //Since this session version never supported a minor protocol version this should be fine
        return new Version(wiproProcolVer,0,0);
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
    public boolean isTransportForServiceAvailable(SessionType sessionType){
        return _sdlConnection!= null
                && _sdlConnection._transport!= null
                && _sdlConnection._transport.getIsConnected()
                && ((sessionType == SessionType.RPC || sessionType == SessionType.CONTROL || sessionType == SessionType.BULK_DATA ) //If this is a service that can run on any transport just return true
                    || (_sdlConnection._transport.getTransportType() == TransportType.USB || _sdlConnection._transport.getTransportType() == TransportType.TCP));
    }


}