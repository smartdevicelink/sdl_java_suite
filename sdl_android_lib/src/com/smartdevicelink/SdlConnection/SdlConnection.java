package com.smartdevicelink.SdlConnection;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import com.smartdevicelink.encoder.SdlEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.IProtocolListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.ServiceType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.transport.*;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlConnection implements IProtocolListener, ITransportListener, IStreamListener  {

	SdlTransport _transport = null;
	AbstractProtocol _protocol = null;
	ISdlConnectionListener _connectionListener = null;
	
	StreamRPCPacketizer mRPCPacketizer = null;
	StreamPacketizer mVideoPacketizer = null;
	StreamPacketizer mAudioPacketizer = null;
	SdlEncoder mSdlEncoder = null;

	// Thread safety locks
	Object TRANSPORT_REFERENCE_LOCK = new Object();
	Object PROTOCOL_REFERENCE_LOCK = new Object();
	
	private CopyOnWriteArrayList<SdlSession> listenerList = new CopyOnWriteArrayList<SdlSession>();
	
	private final static int BUFF_READ_SIZE = 1000000;
	
	/**
	 * Constructor.
	 * 
	 * @param listener Sdl connection listener.
	 * @param transportConfig Transport configuration for this connection.
	 */
	public SdlConnection(BaseTransportConfig transportConfig) {
		_connectionListener = new InternalMsgDispatcher();
		
		// Initialize the transport
		synchronized(TRANSPORT_REFERENCE_LOCK) {
			// Ensure transport is null
			if (_transport != null) {
				if (_transport.getIsConnected()) {
					_transport.disconnect();
				}
				_transport = null;
			}
			
			if (transportConfig.getTransportType() == TransportType.BLUETOOTH)
			{				
				BTTransportConfig myConfig = (BTTransportConfig) transportConfig;				
				_transport = new BTTransport(this, myConfig.getKeepSocketActive());
			}
			else if (transportConfig.getTransportType() == TransportType.TCP)
			{
                _transport = new TCPTransport((TCPTransportConfig) transportConfig, this);
            } else if (transportConfig.getTransportType() == TransportType.USB) {
                _transport = new USBTransport((USBTransportConfig) transportConfig, this);
            }
		}
		
		// Initialize the protocol
		synchronized(PROTOCOL_REFERENCE_LOCK) {
			// Ensure protocol is null
			if (_protocol != null) {
				_protocol = null;
			}
			
			_protocol = new WiProProtocol(this);
		}
	}
	
	public AbstractProtocol getWiProProtocol(){
		return _protocol;
	}
	

	
	
	private void closeConnection(boolean willRecycle, byte rpcSessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK) {
			if (_protocol != null) {
				// If transport is still connected, sent EndProtocolSessionMessage
				if (_transport != null && _transport.getIsConnected()) {
					_protocol.EndProtocolSession(ServiceType.RPC, rpcSessionID);
				}
				if (willRecycle) {
				_protocol = null;
				}
			} // end-if
		}
		
		synchronized (TRANSPORT_REFERENCE_LOCK) {
			if (willRecycle) {
			if (_transport != null) {
				_transport.disconnect();
			}
			_transport = null;
		}
	}
	}
		
	
	public void startTransport() throws SdlException {
		_transport.openConnection();
	}
	
	public Boolean getIsConnected() {
		
		// If _transport is null, then it can't be connected
		if (_transport == null) {
			return false;
		}
		
		return _transport.getIsConnected();
	}
	
	public String getBroadcastComment() {
		
		if (_transport == null) {
			return "";
		}
		
		return _transport.getBroadcastComment();
	}
	
	public void sendMessage(ProtocolMessage msg) {
		if(_protocol != null)
			_protocol.SendMessage(msg);
	}
	
	void startHandShake() {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.StartProtocolSession(ServiceType.RPC);
			}
		}
	}	
	
	@Override
	public void onTransportBytesReceived(byte[] receivedBytes,
			int receivedBytesLength) {
		// Send bytes to protocol to be interpreted 
		synchronized(PROTOCOL_REFERENCE_LOCK) {
			if (_protocol != null) {
				_protocol.HandleReceivedBytes(receivedBytes, receivedBytesLength);
			}
		}
	}

	@Override
	public void onTransportConnected() {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
					for (SdlSession s : listenerList) {
						if (s.getSessionId() == 0) {
							startHandShake();
						}
					}
				}
			}
	}
	
	@Override
	public void onTransportDisconnected(String info) {
		// Pass directly to connection listener
		_connectionListener.onTransportDisconnected(info);
	}

	@Override
	public void onTransportError(String info, Exception e) {
		// Pass directly to connection listener
		_connectionListener.onTransportError(info, e);
	}

	@Override
	public void onProtocolMessageBytesToSend(byte[] msgBytes, int offset,
			int length) {
		// Protocol has packaged bytes to send, pass to transport for transmission 
		synchronized(TRANSPORT_REFERENCE_LOCK) {
			if (_transport != null) {
				_transport.sendBytes(msgBytes, offset, length);
			}
		}
	}
	
	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {
		_connectionListener.onProtocolMessageReceived(msg);
	}

	@Override
	public void onProtocolSessionStarted(ServiceType serviceType,
			byte sessionID, byte version, String correlationID) {
		_connectionListener.onProtocolSessionStarted(serviceType, sessionID, version, correlationID);
	}

	@Override
	public void onProtocolSessionNACKed(ServiceType serviceType,
			byte sessionID, byte version, String correlationID) {
		_connectionListener.onProtocolSessionStartedNACKed(serviceType, sessionID, version, correlationID);
	}

	@Override
	public void onProtocolSessionEnded(ServiceType serviceType, byte sessionID,
			String correlationID) {
		_connectionListener.onProtocolSessionEnded(serviceType, sessionID, correlationID);
	}

	@Override
	public void onProtocolError(String info, Exception e) {
		_connectionListener.onProtocolError(info, e);
	}
	
	/**
	 * Gets type of transport currently used by this connection.
	 * 
	 * @return One of TransportType enumeration values.
	 * 
	 * @see TransportType
	 */
	public TransportType getCurrentTransportType() {
		return _transport.getTransportType();
	}
	public void startStream(InputStream is, ServiceType sType, byte rpcSessionID) throws IOException {
            if (sType.equals(ServiceType.NAV))
            {
            	mVideoPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
            	mVideoPacketizer.sdlConnection = this;
            	mVideoPacketizer.start();
            }
            else if (sType.equals(ServiceType.PCM))
            {
            	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
            	mAudioPacketizer.sdlConnection = this;
            	mAudioPacketizer.start();            	
            }
	}
	
	@SuppressLint("NewApi") public OutputStream startStream(ServiceType sType, byte rpcSessionID) throws IOException {
			OutputStream os = new PipedOutputStream();
			InputStream is = null;
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
				is = new PipedInputStream((PipedOutputStream) os, BUFF_READ_SIZE);
			} else {
				is = new PipedInputStream((PipedOutputStream) os);
			}
            if (sType.equals(ServiceType.NAV))
            {
                mVideoPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
                mVideoPacketizer.sdlConnection = this;
                mVideoPacketizer.start();
            }       
            else if (sType.equals(ServiceType.PCM))
            {
            	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
            	mAudioPacketizer.sdlConnection = this;
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
		
	public void startRPCStream(InputStream is, RPCRequest request, ServiceType sType, byte rpcSessionID, byte wiproVersion) {
		try {
			mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0);
			mRPCPacketizer.start();
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
	}
	
	public OutputStream startRPCStream(RPCRequest request, ServiceType sType, byte rpcSessionID, byte wiproVersion) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0);
			mRPCPacketizer.start();
			return os;
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
		return null;
	}

	public void pauseRPCStream()
	{
		if (mRPCPacketizer != null)
		{
			mRPCPacketizer.pause();
		}
	}

	public void resumeRPCStream()
	{
		if (mRPCPacketizer != null)
		{
			mRPCPacketizer.resume();
		}
	}

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
			int height, int bitrate, ServiceType sType, byte rpcSessionID) {
		try {
			PipedOutputStream stream = (PipedOutputStream) startStream(sType, rpcSessionID);
			if (stream == null) return null;
			mSdlEncoder = new SdlEncoder();
			mSdlEncoder.setFrameRate(frameRate);
			mSdlEncoder.setFrameInterval(iFrameInterval);
			mSdlEncoder.setFrameWidth(width);
			mSdlEncoder.setFrameHeight(height);
			mSdlEncoder.setBitrate(bitrate);
			mSdlEncoder.setOutputStream(stream);
		} catch (IOException e) {
			return null;
		}
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
	
	public void startService (ServiceType serviceType, byte sessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.StartProtocolService(serviceType, sessionID);
			}
		}
	}
	
	public void endService (ServiceType serviceType, byte sessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.EndProtocolSession(serviceType, sessionID);
			}
		}
	}
	void registerSession(SdlSession registerListener) throws SdlException {
		listenerList.addIfAbsent(registerListener);
		
		if (!this.getIsConnected()) {
			this.startTransport();
		} else {
			this.startHandShake();
		}
	}
	
	public void sendHeartbeat(SdlSession mySession) {
		if(_protocol != null && mySession != null)
			_protocol.SendHeartBeat(mySession.getSessionId());
	}	
	
	public void unregisterSession(SdlSession registerListener) {
		listenerList.remove(registerListener);			
		closeConnection(listenerList.size() == 0, registerListener.getSessionId());
	}

	
	private SdlSession findSessionById(byte id) {
			for (SdlSession listener : listenerList) {
				if (listener.getSessionId() == id) {
					return listener;
				}
			}
		return null;
	}	
	
	private class InternalMsgDispatcher implements ISdlConnectionListener {

		@Override
		public void onTransportDisconnected(String info) {
			for (SdlSession session : listenerList) {
				session.onTransportDisconnected(info);
			}
		}

		@Override
		public void onTransportError(String info, Exception e) {
			for (SdlSession session : listenerList) {
				session.onTransportError(info, e);
			}
		}

		@Override
		public void onProtocolMessageReceived(ProtocolMessage msg) {
			SdlSession session = findSessionById(msg.getSessionID());
			if (session != null) {
				session.onProtocolMessageReceived(msg);
			}
		}

		@Override
		public void onProtocolSessionStarted(ServiceType serviceType,
				byte sessionID, byte version, String correlationID) {
			for (SdlSession session : listenerList) {
				if (session.getSessionId() == 0) {
					session.onProtocolSessionStarted(serviceType, sessionID, version, correlationID);
					break;
				}
			}
			if (serviceType.equals(ServiceType.NAV) || serviceType.equals(ServiceType.PCM)){
				SdlSession session = findSessionById(sessionID);
				if (session != null) {
					session.onProtocolSessionStarted(serviceType, sessionID, version, correlationID);
				}
			}
		}

		@Override
		public void onProtocolSessionEnded(ServiceType serviceType,
				byte sessionID, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionEnded(serviceType, sessionID, correlationID);
			}
		}

		@Override
		public void onProtocolError(String info, Exception e) {
			for (SdlSession session : listenerList) {
				session.onProtocolError(info, e);
			}
		}

		@Override
		public void onProtocolSessionStartedNACKed(ServiceType serviceType,
				byte sessionID, byte version, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionStartedNACKed(serviceType, sessionID, version, correlationID);
			}			
		}

		@Override
		public void onHeartbeatTimedOut(byte sessionID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onHeartbeatTimedOut(sessionID);
			}
		}

		@Override
		public void onProtocolSessionEndedNACKed(ServiceType serviceType, byte sessionID, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionEndedNACKed(serviceType, sessionID, correlationID);
			}			
			}

		@Override
		public void onProtocolServiceDataACK(ServiceType serviceType, byte sessionID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolServiceDataACK(serviceType, sessionID);
			}
		}		
	}
		
	public int getRegisterCount() {
		return listenerList.size();
	}
	
	@Override
	public void onProtocolHeartbeat(ServiceType serviceType, byte sessionID) {
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._outgoingHeartbeatMonitor != null) {
    		mySession._outgoingHeartbeatMonitor.heartbeatReceived();
        }
    	if (mySession._incomingHeartbeatMonitor != null) {
    		mySession._incomingHeartbeatMonitor.heartbeatReceived();
        }		
	}
    
	@Override
    public void onProtocolHeartbeatACK(ServiceType serviceType, byte sessionID) {
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._outgoingHeartbeatMonitor != null) {
    		mySession._outgoingHeartbeatMonitor.heartbeatACKReceived();
        }
    	if (mySession._incomingHeartbeatMonitor != null) {
    		mySession._incomingHeartbeatMonitor.heartbeatACKReceived();
        }
    }

    @Override
    public void onResetOutgoingHeartbeat(ServiceType serviceType, byte sessionID){
    	
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._outgoingHeartbeatMonitor != null) {
    		mySession._outgoingHeartbeatMonitor.notifyTransportActivity();
        }
    }

    @Override
    public void onResetIncomingHeartbeat(ServiceType serviceType, byte sessionID){
    	
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._incomingHeartbeatMonitor != null) {
    		mySession._incomingHeartbeatMonitor.notifyTransportActivity();
        }
    }
    
	@Override
	public void onProtocolSessionEndedNACKed(ServiceType serviceType,
			byte sessionID, String correlationID) {
		_connectionListener.onProtocolSessionEndedNACKed(serviceType, sessionID, correlationID);
		
	}

	@Override
	public void onProtocolServiceDataACK(ServiceType serviceType, byte sessionID) {
		_connectionListener.onProtocolServiceDataACK(serviceType, sessionID);
		
	}
}
