package com.smartdevicelink.SdlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import com.smartdevicelink.encoder.SdlEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitor;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitorListener;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.security.ISecurityInitializedListener;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransport;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlSession implements ISdlConnectionListener, IHeartbeatMonitorListener, IStreamListener, ISecurityInitializedListener {
	private static CopyOnWriteArrayList<SdlConnection> shareConnections = new CopyOnWriteArrayList<SdlConnection>();
	private CopyOnWriteArrayList<SessionType> encryptedServices = new CopyOnWriteArrayList<SessionType>();
	
	SdlConnection _sdlConnection = null;
	private byte sessionId;
    private byte wiproProcolVer;
	private ISdlConnectionListener sessionListener;
	private BaseTransportConfig transportConfig;
    IHeartbeatMonitor _outgoingHeartbeatMonitor = null;
    IHeartbeatMonitor _incomingHeartbeatMonitor = null;
    private static final String TAG = "SdlSession";
    private LockScreenManager lockScreenMan  = new LockScreenManager();
    private SdlSecurityBase sdlSecurity = null;    
	StreamRPCPacketizer mRPCPacketizer = null;
	StreamPacketizer mVideoPacketizer = null;
	StreamPacketizer mAudioPacketizer = null;
	SdlEncoder mSdlEncoder = null;
	private final static int BUFF_READ_SIZE = 1024;
    private int sessionHashId = 0;

    
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
		
	
	private SdlSession() {
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
	
	public void close() {
		if (sdlSecurity != null)
		{
			sdlSecurity.resetParams();
			sdlSecurity.shutDown();
		}

		if (_sdlConnection != null) { //sessionId == 0 means session is not started.
			_sdlConnection.unregisterSession(this);
			
			if (_sdlConnection.getRegisterCount() == 0) {
				shareConnections.remove(_sdlConnection);
			}

			_sdlConnection = null;
		}
	}
	
	public void startStream(InputStream is, SessionType sType, byte rpcSessionID) throws IOException {
        if (sType.equals(SessionType.NAV))
        {
        	mVideoPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
        	mVideoPacketizer.sdlConnection = this.getSdlConnection();
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
            mVideoPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
            mVideoPacketizer.sdlConnection = this.getSdlConnection();
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
	
	public void startRPCStream(InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
		try {
			mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0, this);
			mRPCPacketizer.start();
		} catch (Exception e) {
	        Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
	    }
	}

	public OutputStream startRPCStream(RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mRPCPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0, this);
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
			int height, int bitrate, SessionType sType, byte rpcSessionID) {
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
				
				sdlSecurity.initialize();
			}			
			return;
		}
		_sdlConnection.startService(serviceType, sessionID, isEncrypted);		
	}
	
	public void endService (SessionType serviceType, byte sessionID) {
		if (_sdlConnection == null) 
			return;
		_sdlConnection.endService(serviceType, sessionID);	
	}
	
	private void processControlService(ProtocolMessage msg) {
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
	
    private void initialiseSession() {
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
		if (isEncrypted)
			encryptedServices.addIfAbsent(sessionType);
		this.sessionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
		//if (version == 3)
			initialiseSession();
		if (sessionType.eq(SessionType.RPC)){
			sessionHashId = hashID;
		}
	}

	@Override
	public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
			String correlationID) {		
		this.sessionListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
		encryptedServices.remove(sessionType);
	}

	@Override
	public void onProtocolError(String info, Exception e) {
		this.sessionListener.onProtocolError(info, e);
	}
    
    @Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {
        Log.d(TAG, "Asked to send heartbeat");
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
			byte sessionID, byte version, String correlationID) {
		this.sessionListener.onProtocolSessionStartedNACKed(sessionType, sessionID, version, correlationID);		
	}

	@Override
	public void onProtocolSessionEndedNACKed(SessionType sessionType,
			byte sessionID, String correlationID) {
		this.sessionListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
		
	}

	@Override
	public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID) {
		this.sessionListener.onProtocolServiceDataACK(sessionType, dataSize, sessionID);
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
}
