package com.smartdevicelink.SdlConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import com.smartdevicelink.encoder.SdlEncoder;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
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
import com.smartdevicelink.streaming.video.RTPH264Packetizer;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.streaming.video.VideoStreamingParameters;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransport;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.StartServiceACK;
import static com.smartdevicelink.protocol.enums.ControlFrameTags.RPC.TransportEventUpdate;

public class SdlSession implements ISdlConnectionListener, IHeartbeatMonitorListener, IStreamListener, ISecurityInitializedListener {
	private static final String SECONDARY_TRANSPORT_TCP = "TCP_WIFI";
	private static final String SECONDARY_TRANSPORT_USB = "AOA_USB";
	private static final String SECONDARY_TRANSPORT_BT = "SPP_BLUETOOTH";
	private static final String SECONARY_SERVICE_PCM = "0x0A";
	private static final String SECONARY_SERVICE_VIDEO = "0x0B";

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
	AbstractPacketizer mVideoPacketizer = null;
	StreamPacketizer mAudioPacketizer = null;
	SdlEncoder mSdlEncoder = null;
	VirtualDisplayEncoder virtualDisplayEncoder = null;
	private final static int BUFF_READ_SIZE = 1024;
    private int sessionHashId = 0;
	private HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> serviceListeners;
	private VideoStreamingParameters desiredVideoParams = null;
	private VideoStreamingParameters acceptedVideoParams = null;

    private boolean secondaryConnectionEnabled = false;
    private ArrayList<TransportType> secondaryTransportTypes;
    private SdlConnection secondarySdlConnection = null;
    private HashMap<SessionType, SecondaryService> secondaryServices;
	private ArrayList<TransportLevel> audioTransports;
	private ArrayList<TransportLevel> videoTransports;
    
	public static SdlSession createSession(byte wiproVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {
		
		SdlSession session =  new SdlSession();
		session.wiproProcolVer = wiproVersion;
		session.sessionListener = listener;
		session.transportConfig = btConfig;
		session.secondaryConnectionEnabled = true;
					
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
	
	public long getMtu(SessionType type) {
		if (((type == SessionType.NAV) || (type == SessionType.PCM)) && secondaryConnectionEnabled) {
			if ((secondarySdlConnection != null) && isServiceAllowed(type, TransportLevel.SECONDARY)) {
				return secondarySdlConnection.getWiProProtocol().getMtu(type);
			}
		}
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
			_sdlConnection.unregisterSession(this);
			
			if (_sdlConnection.getRegisterCount() == 0) {
				shareConnections.remove(_sdlConnection);
			}

			_sdlConnection = null;
		}
		if (secondarySdlConnection != null) {
			secondarySdlConnection.unregisterSession(this);

			secondarySdlConnection = null;
		}
	}
	
	public void startStream(InputStream is, SessionType sType, byte rpcSessionID) throws IOException {
		SdlConnection connection = null;
		SecondaryService secondaryService = null;
		if ((secondaryConnectionEnabled) && ((sType == SessionType.PCM) || (sType == SessionType.NAV))) {
			boolean allowed = isServiceAllowed(sType, TransportLevel.SECONDARY);
			if ((this.secondarySdlConnection != null) && allowed) {
				connection = secondarySdlConnection;
			} else {
				if (allowed) {
					addPendingService(sType, rpcSessionID, null, null);
					secondaryService = secondaryServices.get(sType);
				}
				if (isServiceAllowed(sType, TransportLevel.PRIMARY)) {
					connection = this.getSdlConnection();
				}
			}
		} else {
			connection = this.getSdlConnection();
		}

		if (sType.equals(SessionType.NAV)) {
        	// protocol is fixed to RAW
        	StreamPacketizer packetizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
			packetizer.sdlConnection = connection;
        	mVideoPacketizer = packetizer;
        	mVideoPacketizer.start();
            if (secondaryService != null) {
            	secondaryService.stream = mVideoPacketizer;
        }
		} else if (sType.equals(SessionType.PCM)) {
        	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
			mAudioPacketizer.sdlConnection = connection;
        	mAudioPacketizer.start();            	
        	if (secondaryService != null) {
            	secondaryService.stream = mAudioPacketizer;
            }
        }
	}

	@SuppressLint("NewApi")
	public OutputStream startStream(SessionType sType, byte rpcSessionID) throws IOException {
		SecondaryService secondaryService = null;
		SdlConnection connection = null;
		if (secondaryConnectionEnabled && ((sType == SessionType.NAV) || (sType == SessionType.PCM))) {
			boolean allowed = isServiceAllowed(sType, TransportLevel.SECONDARY);
			if ((this.secondarySdlConnection != null) && allowed) {
				connection = this.secondarySdlConnection;
			} else {
				if (allowed) {
					addPendingService(sType, rpcSessionID, null, null);
					secondaryService = secondaryServices.get(sType);
				}
				if (isServiceAllowed(sType, TransportLevel.PRIMARY)) {
					connection = this.getSdlConnection();
				}
			}
		} else {
			connection = this.getSdlConnection();
		}

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
            packetizer.sdlConnection = connection;
            mVideoPacketizer = packetizer;
            mVideoPacketizer.start();
            if (secondaryService != null) {
            	secondaryService.stream = mVideoPacketizer;
            }
        }       
        else if (sType.equals(SessionType.PCM))
        {
        	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
        	mAudioPacketizer.sdlConnection = connection;
        	mAudioPacketizer.start();            	
            if (secondaryService != null) {
            	secondaryService.stream = mAudioPacketizer;
            }
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
					SdlConnection connection = null;
					if (secondaryConnectionEnabled) {
						boolean allowed = isServiceAllowed(SessionType.PCM, TransportLevel.SECONDARY);
						if ((this.secondarySdlConnection != null) && allowed) {
							connection = this.secondarySdlConnection;
						} else {
							if (allowed) {
								addPendingService(SessionType.NAV, rpcSessionID, null, packetizer);
							}
							if (isServiceAllowed(SessionType.NAV, TransportLevel.PRIMARY)) {
								connection = this.getSdlConnection();
							}
						}
					}
					packetizer.sdlConnection = connection;
					mVideoPacketizer = packetizer;
					mVideoPacketizer.start();
					return packetizer;
				}
				case RTP: {
					RTPH264Packetizer packetizer = new RTPH264Packetizer(this, SessionType.NAV, rpcSessionID, this);
					SdlConnection connection = null;
					if (secondaryConnectionEnabled) {
						boolean allowed = isServiceAllowed(SessionType.NAV, TransportLevel.SECONDARY);
						if ((this.secondarySdlConnection != null) && allowed) {
							connection = this.secondarySdlConnection;
						} else {
							if (allowed) {
								addPendingService(SessionType.NAV, rpcSessionID, null, packetizer);
							}
							if (isServiceAllowed(SessionType.NAV, TransportLevel.PRIMARY)) {
								connection = this.getSdlConnection();
							}
						}
					}
					packetizer.sdlConnection = connection;
					mVideoPacketizer = packetizer;
					mVideoPacketizer.start();
					return packetizer;
				}
				default:
					Log.e(TAG, "Protocol " + protocol + " is not supported.");
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
			SdlConnection connection = null;
			if (secondaryConnectionEnabled) {
				boolean allowed = isServiceAllowed(SessionType.PCM, TransportLevel.SECONDARY);
				if ((this.secondarySdlConnection != null) && allowed) {
					connection = this.secondarySdlConnection;
				} else {
					if (allowed) {
						addPendingService(SessionType.PCM, rpcSessionID, null, packetizer);
					}
					if (isServiceAllowed(SessionType.PCM, TransportLevel.PRIMARY)) {
						connection = this.getSdlConnection();
					}
				}
			}
			packetizer.sdlConnection = connection;
			mAudioPacketizer = packetizer;
			mAudioPacketizer.start();
			return packetizer;
		} catch (IOException e) {
			return null;
		}
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
				
				sdlSecurity.initialize();
			}			
			return;
		}

		if (((serviceType != SessionType.NAV) && (serviceType != SessionType.PCM))) {
			_sdlConnection.startService(serviceType, sessionID, isEncrypted);
		} else {
			boolean allowed = isServiceAllowed(serviceType, TransportLevel.SECONDARY);
			if ((secondarySdlConnection != null) && allowed) {
				secondarySdlConnection.startService(serviceType, sessionID, isEncrypted);
			} else {
				if (allowed) {
					addPendingService(serviceType, sessionID, isEncrypted, null);
				}
				if (isServiceAllowed(serviceType, TransportLevel.PRIMARY)) {
					_sdlConnection.startService(serviceType, sessionID, isEncrypted);		
 				}
			}
		}
	}
	
	public void endService (SessionType serviceType, byte sessionID) {
		if ((serviceType == SessionType.NAV) || (serviceType == SessionType.PCM)) {
			if ((secondarySdlConnection != null) && isServiceAllowed(serviceType, TransportLevel.SECONDARY)) {
				secondarySdlConnection.endService(serviceType, sessionID);
				return;
			}
		}
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
		if (secondaryConnectionEnabled && ((msg.getSessionType() == SessionType.NAV) ||
				(msg.getSessionType() == SessionType.PCM))) {
			if ((secondarySdlConnection != null) && isServiceAllowed(msg.getSessionType(), TransportLevel.SECONDARY)) {
				secondarySdlConnection.sendMessage(msg);
			} else if ((_sdlConnection != null) && isServiceAllowed(msg.getSessionType(), TransportLevel.PRIMARY)) {
				_sdlConnection.sendMessage(msg);
			}
			return;
		}

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

	void onTransportDisconnected(String info, TransportType transportType) {
		if (!secondaryConnectionEnabled || (transportType == transportConfig.getTransportType())) {
			onTransportDisconnected(info);
			if (secondarySdlConnection != null) {
				// Secondary transport must not be live if primary transport goes down
				secondarySdlConnection.unregisterSession(this);
				secondarySdlConnection = null;
			}
		} else {
			// Don't notify higher about secondary transport disconnect
			if (secondarySdlConnection != null) {
				secondarySdlConnection.unregisterSession(this);
				secondarySdlConnection = null;
				// retry?
			}
		}
	}

	@Override
	public void onTransportError(String info, Exception e) {
		this.sessionListener.onTransportError(info, e);
	}

	void onTransportError(String info, TransportType transportType, Exception e) {
		if (!secondaryConnectionEnabled || (transportType == transportConfig.getTransportType())) {
			onTransportError(info, e);
		} else {
			// Don't notify higher about secondary transport error
			if (secondarySdlConnection != null) {
				secondarySdlConnection.unregisterSession(this);
				secondarySdlConnection = null;
				// retry?
			}
		}
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
		if ((this.sessionId != 0) && (sessionID == this.sessionId) && (sessionType == SessionType.RPC)) {
			// This is just for SDLCore's notification about the secondary transport, no need to do
			// all the session initialization again or to notify listeners.
			return;
		}

		this.sessionId = sessionID;
		lockScreenMan.setSessionID(sessionID);
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
		if (sessionType.eq(SessionType.RPC)){
			sessionHashId = hashID;
		}
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

	void onProtocolSessionEnded(SessionType sessionType, byte sessionId, String correlationID,
	                            TransportType transportType) {
		if (secondarySdlConnection != null) {
			TransportType primaryTransportType = transportConfig.getTransportType();
			if (transportType == primaryTransportType) {
				// Primary transport ended, close secondary transport
				secondarySdlConnection.unregisterSession(this);
				secondarySdlConnection = null;
			}
		}

		onProtocolSessionEnded(sessionType, sessionId, correlationID);
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
	public void onSecurityInitialized() {
		
		if (_sdlConnection != null && sdlSecurity != null)
		{
			List<SessionType> list = sdlSecurity.getServiceList();
			
			SessionType service;			
			ListIterator<SessionType> iter = list.listIterator();
			
			while (iter.hasNext()) {
				service = iter.next();
				
				if (service != null) {
					if ((service != SessionType.NAV) && (service != SessionType.PCM)) {
						_sdlConnection.startService(service, getSessionId(), true);
					} else {
						boolean allowed = isServiceAllowed(service, TransportLevel.SECONDARY);

						if ((secondarySdlConnection != null) && allowed) {
							secondarySdlConnection.startService(service, getSessionId(), true);
						} else {
							if (secondaryConnectionEnabled && allowed) {
								addPendingService(service, getSessionId(),true, null);
							}

							if (isServiceAllowed(service, TransportLevel.PRIMARY)) {
					_sdlConnection.startService(service, getSessionId(), true);
							}
						}
					}
				}
				
				iter.remove();				
			}
		}					
	}
	
	public void clearConnection(){
		_sdlConnection = null;
		secondarySdlConnection = null;
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

	public void handleStartSessionACK(SdlPacket startSessionACKPacket, TransportType transportType) {
		if (!isPrimaryBluetoothTransport()) {
			// Only BT transports as primary need secondary transport
			secondaryConnectionEnabled = false;
			return;
		}

		if (transportType == transportConfig.getTransportType()) {
			secondaryServices = null;
			audioTransports = null;
			videoTransports = null;

			if (startSessionACKPacket.getVersion() >= 5) {
				String secondaryTransport = (String) startSessionACKPacket.getTag(
						StartServiceACK.SECONDARY_TRANSPORT);

				secondaryConnectionEnabled = (secondaryTransport != null);
				if (secondaryConnectionEnabled) {
					int tcpPos = secondaryTransport.indexOf(SECONDARY_TRANSPORT_TCP);
					int usbPos = secondaryTransport.indexOf(SECONDARY_TRANSPORT_USB);
					if ((tcpPos >= 0) || (usbPos >= 0)) {
						//noinspection unchecked
						ArrayList<Integer> audio = (ArrayList<Integer>) startSessionACKPacket.getTag(
								StartServiceACK.AUDIO_SERVICE_TRANSPORTS);
						ArrayList<Integer> video = (ArrayList<Integer>) startSessionACKPacket.getTag(
								StartServiceACK.VIDEO_SERVICE_TRANSPORTS);

						audioTransports = buildServicesList(audio);
						videoTransports = buildServicesList(video);
						secondaryTransportTypes = new ArrayList<>();
						if (tcpPos >= 0) {
							secondaryTransportTypes.add(TransportType.TCP);
						}
						if (usbPos >= 0) {
							if (usbPos > tcpPos) {
								secondaryTransportTypes.add(TransportType.USB);
							} else {
								usbPos = secondaryTransportTypes.indexOf(TransportType.TCP);
								if (usbPos < 0) {
									usbPos = 0;
								}
								secondaryTransportTypes.add(usbPos, TransportType.USB);
							}
						}
					} else {
						secondaryConnectionEnabled = false;
					}
				}
			} else {
				secondaryConnectionEnabled = false;
			}

			if (secondaryConnectionEnabled) {
				secondaryServices = new HashMap<>();
			}
		} else if ((secondarySdlConnection != null) &&
				(secondarySdlConnection.getCurrentTransportType() == transportType)) {
			startStreamingServices();
		}
	}

	private boolean isPrimaryBluetoothTransport() {
		TransportType transportType = transportConfig.getTransportType();
		return (transportType == TransportType.BLUETOOTH) ||
				(transportType == TransportType.MULTIPLEX);
	}

	private ArrayList<TransportLevel> buildServicesList(ArrayList<Integer> transports) {
		ArrayList<TransportLevel> levels = new ArrayList<>();
		if ((transports != null) && (transports.size() > 0)) {
			for (int i = 0; i < transports.size(); i++) {
				Integer level = transports.get(i);
				switch (level) {
					case 1:
						levels.add(TransportLevel.PRIMARY);
						break;
					case 2:
						levels.add(TransportLevel.SECONDARY);
						break;
				}
			}
		}

		levels.trimToSize();
		return levels;
	}

	private boolean isServiceAllowed(SessionType type, TransportLevel level) {
		// default to allowed for backward compatibility
		boolean allowed = (level == TransportLevel.PRIMARY);

		if ((type == SessionType.PCM) && (audioTransports != null)) {
			allowed = audioTransports.contains(level);
		} else if ((type == SessionType.NAV) && (videoTransports != null)) {
			allowed = videoTransports.contains(level);
		}

		return allowed;
	}

	private void startStreamingServices() {
		if (secondarySdlConnection != null) {
			// Start up previously requested services on secondary connection
			if (!secondaryServices.isEmpty()) {
				Set<SessionType> keys = secondaryServices.keySet();
				for (SessionType type : keys) {
					SecondaryService service = secondaryServices.get(type);
					if (service != null) {
						secondarySdlConnection.startService(type,
								service.sessionId, service.isEncrypted);

						if (service.stream instanceof StreamPacketizer) {
							((StreamPacketizer) service.stream).sdlConnection = secondarySdlConnection;
						} else if (service.stream instanceof RTPH264Packetizer) {
							((RTPH264Packetizer) service.stream).sdlConnection = secondarySdlConnection;
						}

						_sdlConnection.endService(type, service.sessionId);
					}
				}
				secondaryServices.clear();
			} else {
				if ((mAudioPacketizer != null) && isServiceAllowed(SessionType.PCM, TransportLevel.SECONDARY)) {
						secondarySdlConnection.startService(SessionType.PCM,
								sessionId, (sdlSecurity != null));
				}
			}
		}
	}

	public void handleTransportConfigUpdate(SdlPacket packet) {
		String ipAddr = (String) packet.getTag(TransportEventUpdate.TCP_IP_ADDRESS);
		Integer port = (Integer) packet.getTag(TransportEventUpdate.TCP_PORT);
		// TODO: get USB transport related data from packet
		if ((secondaryConnectionEnabled) && (secondaryTransportTypes != null)) {
			if ((ipAddr == null) && (port == null)) {
				// empty frame data indicates that TCP transport is not available
				if (secondarySdlConnection != null) {
					secondarySdlConnection.endService(SessionType.NAV, sessionId);
					if (mVideoPacketizer != null) {
						if (mVideoPacketizer instanceof StreamPacketizer) {
							((StreamPacketizer) mVideoPacketizer).sdlConnection = null;
						} else if (mVideoPacketizer instanceof RTPH264Packetizer) {
							((RTPH264Packetizer) mVideoPacketizer).sdlConnection = null;
						}
						if ((_sdlConnection != null) && isServiceAllowed(SessionType.NAV, TransportLevel.PRIMARY)) {
							_sdlConnection.startService(SessionType.NAV, sessionId, (sdlSecurity != null));
							if (mVideoPacketizer instanceof StreamPacketizer) {
								((StreamPacketizer) mVideoPacketizer).sdlConnection = _sdlConnection;
							} else if (mVideoPacketizer instanceof RTPH264Packetizer) {
								((RTPH264Packetizer) mVideoPacketizer).sdlConnection = _sdlConnection;
							}
						} else {
							stopVideoStream();
						}
					}

					secondarySdlConnection.endService(SessionType.PCM, sessionId);
					if (mAudioPacketizer != null) {
						mAudioPacketizer.sdlConnection = null;
						if ((_sdlConnection != null) && isServiceAllowed(SessionType.PCM, TransportLevel.PRIMARY)) {
							_sdlConnection.startService(SessionType.PCM, sessionId, (sdlSecurity != null));
							mAudioPacketizer.sdlConnection = _sdlConnection;
						} else {
							stopAudioStream();
						}
					}
					secondarySdlConnection.unregisterSession(this);
					secondarySdlConnection = null;
				}
			} else if ((ipAddr != null) && (port != null)) {
				if (secondaryTransportTypes.contains(TransportType.TCP)) {
					startTCPTransport(ipAddr, port);
				}
			}

			// TODO: we need some indication in start session ACK to indicate that USB transport is available
//	        if (some indication of USB transport available)
//		        if (transport config data for USB missing) {
					// empty frame data indicates that TCP transport is not available
					// TODO: disconnect USB transport
//              } else if (all USB transport config data is available) {
//		            startUSBTransport(some USB parameters);
//              }
//	        }
		}
	}

	private void addPendingService(SessionType type, byte sessId, Boolean isEncrypted, Object stream) {
		SecondaryService svc = secondaryServices.get(type);
		if (svc == null) {
			svc = new SecondaryService(sessId, false, stream);
			svc.isEncrypted = (isEncrypted != null) && isEncrypted;
			secondaryServices.put(type, svc);
		} else {
			if (isEncrypted != null) {
				svc.isEncrypted = isEncrypted;
			}
			if (stream != null) {
				svc.stream = stream;
			}
			if ((svc.sessionId == this.sessionId) && (sessId != svc.sessionId)) {
				svc.sessionId = sessId;
			}
		}
	}

	private void startTCPTransport(String ipAddr, int port) {
		TCPTransportConfig transportConfig = new TCPTransportConfig(port, ipAddr, false);
		SdlConnection connection = new SdlConnection(transportConfig);

		try {
			connection.registerSession(this);
			secondarySdlConnection = connection;
		} catch (SdlException e) {
			Log.w(TAG, "secondary transport connection failed", e);
			// retry?
			connection.unregisterSession(this);
		}
	}

	private void startUSBTransport(HashMap<String, Object> config) {
		// TODO: this is not specified in the proposal so it can't be implemented yet
	}

	private static class SecondaryService {
		SecondaryService(byte sessionID, boolean isEncrypted, Object stream) {
			this.sessionId = sessionID;
			this.isEncrypted = isEncrypted;
			this.stream = stream;
		}

		Object stream;
		byte sessionId;
		boolean isEncrypted;
	}

	private enum TransportLevel {
		PRIMARY,
		SECONDARY
	}
}
