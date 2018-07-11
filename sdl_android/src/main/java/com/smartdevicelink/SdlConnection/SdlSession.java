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
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.Surface;

import com.smartdevicelink.encoder.SdlEncoder;
import com.smartdevicelink.encoder.VirtualDisplayEncoder;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.exception.SdlExceptionCause;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.IProtocolListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.ControlFrameTags;
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
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.transport.TransportManager;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlSession implements  IProtocolListener, TransportManager.TransportEventListener, IHeartbeatMonitorListener, IStreamListener, ISecurityInitializedListener {
	private static final String TAG = "SdlSession";


	private byte sessionId;
	private ISdlConnectionListener sessionListener;
	private BaseTransportConfig transportConfig;

    private LockScreenManager lockScreenMan  = new LockScreenManager();
    private SdlSecurityBase sdlSecurity = null;
	private final static int BUFF_READ_SIZE = 1024;
    private int sessionHashId = 0;
	private HashMap<SessionType, CopyOnWriteArrayList<ISdlServiceListener>> serviceListeners;
	private VideoStreamingParameters desiredVideoParams = null;
	private VideoStreamingParameters acceptedVideoParams = null;
	private CopyOnWriteArrayList<SessionType> encryptedServices = new CopyOnWriteArrayList<SessionType>();

	IHeartbeatMonitor _outgoingHeartbeatMonitor = null;
	IHeartbeatMonitor _incomingHeartbeatMonitor = null;
	StreamRPCPacketizer mRPCPacketizer = null;
	AbstractPacketizer mVideoPacketizer = null;
	StreamPacketizer mAudioPacketizer = null;
	SdlEncoder mSdlEncoder = null;
	VirtualDisplayEncoder virtualDisplayEncoder = null;

	protected TransportManager transportManager;
	protected WiProProtocol wiProProtocol;


	@Deprecated
	public static SdlSession createSession(byte wiproVersion, ISdlConnectionListener listener, BaseTransportConfig btConfig) {
		
		SdlSession session =  new SdlSession();
		session.sessionListener = listener;
		session.transportConfig = btConfig;
					
		return session;
	}

	public SdlSession(ISdlConnectionListener listener, MultiplexTransportConfig config){
		transportConfig = config;
		sessionListener = listener;
		wiProProtocol = new WiProProtocol(this);
		wiProProtocol.setPrimaryTransports(config.getPrimaryTransports());
		wiProProtocol.setRequiresHighBandwidth(config.requiresHighBandwidth());

		transportManager = new TransportManager(config,this);

	}
	private SdlSession(){

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

	@Deprecated
	public SdlConnection getSdlConnection() {
		return null;
	}
	
	public int getMtu(){
		if(this.wiProProtocol!=null){
			return this.wiProProtocol.getMtu();
		}else{
			return 0;
		}
	}
	
	public long getMtu(SessionType type) {
		if (this.wiProProtocol != null) {
			return this.wiProProtocol.getMtu(type);
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
		if(transportManager != null){
			wiProProtocol.EndProtocolSession(SessionType.RPC, sessionId, sessionHashId);
			transportManager.close(sessionId);
		}
	}


	public void startStream(InputStream is, SessionType sType, byte rpcSessionID) throws IOException {
        if (sType.equals(SessionType.NAV))
        {
        	// protocol is fixed to RAW
        	StreamPacketizer packetizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
        	packetizer.setProtocol(wiProProtocol);
        	mVideoPacketizer = packetizer;
        	mVideoPacketizer.start();
        }
        else if (sType.equals(SessionType.PCM))
        {
        	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
        	mAudioPacketizer.setProtocol(wiProProtocol);
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
            packetizer.setProtocol(wiProProtocol);
			mVideoPacketizer = packetizer;
            mVideoPacketizer.start();
        }       
        else if (sType.equals(SessionType.PCM))
        {
        	mAudioPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID, this);
        	mAudioPacketizer.setProtocol(wiProProtocol);
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
					packetizer.setProtocol(wiProProtocol);
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
			mAudioPacketizer = packetizer;
			mAudioPacketizer.setProtocol(wiProProtocol);
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
		if (transportManager == null){
			return;
		}
		
		if (isEncrypted){
			if (sdlSecurity != null){
				List<SessionType> serviceList = sdlSecurity.getServiceList(); 
				if (!serviceList.contains(serviceType))
					serviceList.add(serviceType);
				
				sdlSecurity.initialize();
			}			
			return;
		}
		wiProProtocol.startService(serviceType, sessionID, isEncrypted, wiProProtocol.getTransportForSession(serviceType));
		//wiProProtocol.StartProtocolService(serviceType, sessionID, isEncrypted);
	}
	
	public void endService (SessionType serviceType, byte sessionID) {
		if (transportManager == null) {
			return;
		}
		wiProProtocol.EndProtocolService(serviceType,sessionID);
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
		protocolMessage.setVersion(wiProProtocol.getMajorVersionByte());
		protocolMessage.setSessionID(getSessionId());
			
		//sdlSecurity.hs();
		
		sendMessage(protocolMessage);
	}
	
	public String getBroadcastComment(BaseTransportConfig myTransport) {
		return "Multiplexing";
	}
	
	
	public void startSession() throws SdlException {
		transportManager.start();
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
		if (wiProProtocol == null){
			return;
		}
		wiProProtocol.SendMessage(msg);
	}
	
	public TransportType getCurrentTransportType() {
		if (transportManager == null) {
			return null;
		}
		return TransportType.MULTIPLEX;
		//return transportManager.getPrimaryTransport();
	}
	
	public boolean getIsConnected() {
		return transportManager != null && transportManager.isConnected(null);
	}
	
	public boolean isServiceProtected(SessionType sType) {
		return encryptedServices.contains(sType);
	}

	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  Transport  ************************************************************************************
	 *************************************************************************************************************************************************************************/


	@Override
	public void onPacketReceived(SdlPacket packet) {
		if(wiProProtocol != null){
			wiProProtocol.handlePacketReceived(packet);
		}
	}

	@Override
	public void onTransportConnected(TransportType[] transportTypes) {
		if(wiProProtocol != null){
			Log.d(TAG, "onTransportConnected");
			//In the future we should move this logic into the Protocol Layer
			TransportType type = wiProProtocol.getTransportForSession(SessionType.RPC);
			if(type == null){ //There is currently no transport registered to the
				transportManager.requestNewSession(wiProProtocol.getPreferredPrimaryTransport(transportTypes));
			}
			wiProProtocol.onTransportsConnectedUpdate(transportTypes);
			wiProProtocol.printActiveTransports();
		}

	}

	@Override
	public void onTransportDisconnected(String info, TransportType type) {
		if (type == null) {
			Log.d(TAG, "onTransportDisconnected");
			return;
		} else {
			Log.d(TAG, "onTransportDisconnected - " + type.name());
		}

		if(type.equals(wiProProtocol.getTransportForSession(SessionType.NAV))){
			stopVideoStream();
			Log.d(TAG, "Stopping video stream.");
		}
		if(type.equals(wiProProtocol.getTransportForSession(SessionType.PCM))){
			stopAudioStream();
			Log.d(TAG, "Stopping audio stream.");
		}

		Log.d(TAG, "rpc transport? - " + wiProProtocol.getTransportForSession(SessionType.RPC));
		if(type != null && type.equals(wiProProtocol.getTransportForSession(SessionType.RPC))){
			final MultiplexTransportConfig config = (MultiplexTransportConfig)this.transportConfig;
			List<TransportType> transportTypes = config.getPrimaryTransports();
			//transportTypes.remove(type);
			boolean primaryTransportAvailable = false;
			if(transportTypes.size() > 1){
				for (TransportType transportType: transportTypes){ Log.d(TAG, "Checking " + transportType.name());
					if( type != null && !type.equals(transportType)
							&& transportManager != null
							&& transportManager.isConnected(transportType)){
						Log.d(TAG, "Found a suitable transport");
						primaryTransportAvailable = true;
						((MultiplexTransportConfig) this.transportConfig).setService(transportManager.getRouterService());
						break;
					}
				}
			}
			this.transportManager.close(this.sessionId);
			this.transportManager = null;
			this.sessionListener.onTransportDisconnected(info, primaryTransportAvailable, (MultiplexTransportConfig)this.transportConfig);

		}

	}

	@Override
	public boolean onLegacyModeEnabled(String info){
		//Clear our wiproprotocol and await a connection from the legacy transport
		Log.d(TAG, info);
		MultiplexTransportConfig config = (MultiplexTransportConfig)transportConfig;
		if(config.getPrimaryTransports().contains(TransportType.BLUETOOTH)
				&& !config.requiresHighBandwidth()){
			Log.d(TAG, "Entering legacy mode; creating new protocol instance");
			wiProProtocol = new WiProProtocol(this);
			wiProProtocol.setPrimaryTransports(((MultiplexTransportConfig)transportConfig).getPrimaryTransports());
			return true;
		}else{
			Log.d(TAG, "Bluetooth is not an acceptable transport; not moving to legacy mode");
			return false;
		}
	}

	public void onError(String info){
		shutdown(info);
	}

	public void shutdown(String info){
		Log.d(TAG, "Shutdown - " + info);
		this.sessionListener.onTransportDisconnected(info);

	}

	//OLD

	public void onTransportDisconnected(String info){ }
	public void onTransportError(String info, Exception e){	}


	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  IProtocol Listener  ********************************************************************************
	 *************************************************************************************************************************************************************************/

	@Override
	public void onProtocolMessageBytesToSend(SdlPacket packet) {
		//Log.d(TAG, "onProtocolMessageBytesToSend - " + packet.getTransportType());
		if(transportManager != null){
			transportManager.sendPacket(packet);
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
	public void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {
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

	@Override
	public void onProtocolError(String info, Exception e) {
		this.sessionListener.onProtocolError(info, e);
	}

	@Override
	public void onEnableSecondaryTransport(byte sessionID, ArrayList<String> secondaryTransports,
	        ArrayList<Integer> audioTransports, ArrayList<Integer> videoTransports) {
		List<TransportType> supportedTransports = new ArrayList<>();
		if(secondaryTransports != null) {
			for (String s : secondaryTransports) {
				Log.d(TAG, "Secondary transports allowed by core: " + s);
				if(s.equals(TransportConstants.TCP_WIFI)){
					supportedTransports.add(TransportType.TCP);
				}else if(s.equals(TransportConstants.AOA_USB)){
					supportedTransports.add(TransportType.USB);
				}else if(s.equals(TransportConstants.SPP_BLUETOOTH)){
					supportedTransports.add(TransportType.BLUETOOTH);
				}
			}
			wiProProtocol.setSupportedSecondaryTransports(supportedTransports);
			wiProProtocol.setSupportedServices(SessionType.PCM, audioTransports);
			wiProProtocol.setSupportedServices(SessionType.NAV, videoTransports);
		}else{
			Log.d(TAG, "No secondary transports allowed.");
		}
	}

	@Override
	public void onTransportEventUpdate(byte sessionId, Map<String, Object> params) {
		Log.d(TAG, "Transport Event Update Received From Core");
		transportManager.sendSecondaryTransportDetails(sessionId, params);
	}

	@Override
	public void onRegisterSecondaryTransportACK(byte sessionID) {
		Log.d(TAG, "RegisterSecondaryTransportACK");
		wiProProtocol.setRegisteredForTransport(true);
	}

	@Override
	public void onRegisterSecondaryTransportNACKed(byte sessionID, String reason) {
		Log.d(TAG, "RegisterSecondaryTransportNACK" + reason);
		wiProProtocol.setRegisteredForTransport(false);
	}

	@Override
    public void sendHeartbeat(IHeartbeatMonitor monitor) {
        Log.d(TAG, "Asked to send heartbeat");
        if (wiProProtocol != null)
			wiProProtocol.SendHeartBeat(sessionId);
    }

    @Override
    public void heartbeatTimedOut(IHeartbeatMonitor monitor) {     
        close();
    }

    public void onHeartbeatTimedOut(byte sessionId){
		//TODO
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
	public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) {
		//TODO
	}

	@Override
	public void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {
		//TODO
	}

	@Override
	public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID) {
		this.sessionListener.onProtocolServiceDataACK(sessionType, dataSize, sessionID);
	}

	@Override
	public void onResetOutgoingHeartbeat(SessionType sessionType, byte sessionID) {
		//TODO
	}

	@Override
	public void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID) {
		//TODO
	}

	/* ***********************************************************************************************************************************************************************
	 * *****************************************************************  Security Listener  *********************************************************************************
	 *************************************************************************************************************************************************************************/


	@Override
	public void onSecurityInitialized() {
		
		if (wiProProtocol != null && sdlSecurity != null)
		{
			List<SessionType> list = sdlSecurity.getServiceList();
			
			SessionType service;			
			ListIterator<SessionType> iter = list.listIterator();
			
			while (iter.hasNext()) {
				service = iter.next();
				
				if (service != null)
					wiProProtocol.StartProtocolService(service, getSessionId(), true);
				
				iter.remove();				
			}
		}					
	}

	@Deprecated
	public void clearConnection(){
	}

	@Deprecated
	public static void removeConnection(SdlConnection connection){
	}

	@Deprecated
	public void checkForOpenMultiplexConnection(SdlConnection connection){
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
}
