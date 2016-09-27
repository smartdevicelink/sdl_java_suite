package com.smartdevicelink.SdlConnection;


import java.util.concurrent.CopyOnWriteArrayList;

import android.content.ComponentName;
import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.IProtocolListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.SdlPacket;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.BTTransport;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.ITransportListener;
import com.smartdevicelink.transport.MultiplexTransport;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.RouterServiceValidator;
import com.smartdevicelink.transport.SdlBroadcastReceiver;
import com.smartdevicelink.transport.SdlTransport;
import com.smartdevicelink.transport.TCPTransport;
import com.smartdevicelink.transport.TCPTransportConfig;
import com.smartdevicelink.transport.USBTransport;
import com.smartdevicelink.transport.USBTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlConnection implements IProtocolListener, ITransportListener {

	private static final String TAG = "SdlConnection";
	
	SdlTransport _transport = null;
	AbstractProtocol _protocol = null;
	ISdlConnectionListener _connectionListener = null;
	


	// Thread safety locks
	static Object TRANSPORT_REFERENCE_LOCK = new Object();
	Object PROTOCOL_REFERENCE_LOCK = new Object();
	
	private Object SESSION_LOCK = new Object();
	private CopyOnWriteArrayList<SdlSession> listenerList = new CopyOnWriteArrayList<SdlSession>();
	private static TransportType legacyTransportRequest = null;
	private final static int BUFF_READ_SIZE = 1000000;
	protected static MultiplexTransportConfig cachedMultiConfig = null;
	
	/**
	 * Constructor.
	 * 
	 * @param listener Sdl connection listener.
	 * @param transportConfig Transport configuration for this connection.
	 */
	public SdlConnection(BaseTransportConfig transportConfig) {
		RouterServiceValidator vlad = null;
		//Let's check if we can even do multiplexing
		if(transportConfig.getTransportType() == TransportType.MULTIPLEX){
			ComponentName tempCompName = SdlBroadcastReceiver.consumeQueuedRouterService();
			if(tempCompName!=null){
				vlad =new RouterServiceValidator(((MultiplexTransportConfig)transportConfig).getContext(),tempCompName);
			}else{
				vlad =new RouterServiceValidator(((MultiplexTransportConfig)transportConfig).getContext());
			}
			//vlad.setFlags(RouterServiceValidator.FLAG_DEBUG_VERSION_CHECK);
		}
		constructor(transportConfig,vlad);
	}
	//For unit tests
	protected SdlConnection(BaseTransportConfig transportConfig,RouterServiceValidator rsvp){
		constructor(transportConfig,rsvp);
	}
	
	private void constructor(BaseTransportConfig transportConfig,RouterServiceValidator rsvp){
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
			
			//Let's check if we can even do multiplexing
			if(!isLegacyModeEnabled() &&
					rsvp!= null && 
					transportConfig.getTransportType() == TransportType.MULTIPLEX){
				//rsvp = new RouterServiceValidator(((MultiplexTransportConfig)transportConfig).getContext());
				//vlad.setFlags(RouterServiceValidator.FLAG_DEBUG_VERSION_CHECK);
				if(rsvp.validate()){
					Log.w(TAG, "SDL Router service is valid; attempting to connect");
					((MultiplexTransportConfig)transportConfig).setService(rsvp.getService());//Let thes the transport broker know which service to connect to
				}else{
					Log.w(TAG, "SDL Router service isn't trusted. Enabling legacy bluetooth connection.");	
					if(cachedMultiConfig == null){
						cachedMultiConfig = (MultiplexTransportConfig) transportConfig;
						cachedMultiConfig.setService(null);
					}
					enableLegacyMode(true,TransportType.BLUETOOTH); //We will use legacy bluetooth connection for this attempt
				}
			}
			
			if(!isLegacyModeEnabled() && //Make sure legacy mode is not enabled
					(transportConfig.getTransportType() == TransportType.MULTIPLEX)){
				_transport = new MultiplexTransport((MultiplexTransportConfig)transportConfig,this);
			}else if(isLegacyModeEnabled() && legacyTransportRequest == TransportType.BLUETOOTH){
				_transport = new BTTransport(this, true); 
			}else if(transportConfig.getTransportType() == TransportType.BLUETOOTH){
				_transport = new BTTransport(this,((BTTransportConfig)transportConfig).getKeepSocketActive());
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
	

	
	
	private void closeConnection(boolean willRecycle, byte rpcSessionID, int sessionHashId) {
		synchronized(PROTOCOL_REFERENCE_LOCK) {

			if (_protocol != null) {
				// If transport is still connected, sent EndProtocolSessionMessage
				if (_transport != null && _transport.getIsConnected()) {
					_protocol.EndProtocolSession(SessionType.RPC, rpcSessionID, sessionHashId);
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
				_protocol.StartProtocolSession(SessionType.RPC);
			}
		}
	}	
	
	@Override
	public void onTransportPacketReceived(SdlPacket packet) {
		// Send bytes to protocol to be interpreted 
		synchronized(PROTOCOL_REFERENCE_LOCK) {
			if (_protocol != null) {
				_protocol.handlePacketReceived(packet);
			}
		}
	}

	@Override
	public void onTransportConnected() {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				boolean shouldRequestSession = _transport !=null  && _transport.getTransportType()== TransportType.MULTIPLEX;
					for (SdlSession s : listenerList) {
						if (s.getSessionId() == 0) {
							if(shouldRequestSession){
								((MultiplexTransport)_transport).requestNewSession();
							}
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
	public void onProtocolMessageBytesToSend(SdlPacket packet) {
		// Protocol has packaged bytes to send, pass to transport for transmission 
		synchronized(TRANSPORT_REFERENCE_LOCK) {
			if (_transport != null) {
				_transport.sendBytes(packet);
			}
		}
	}
	
	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {
		_connectionListener.onProtocolMessageReceived(msg);
	}

	@Override
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {
		_connectionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
	}

	@Override
	public void onProtocolSessionNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		_connectionListener.onProtocolSessionStartedNACKed(sessionType, sessionID, version, correlationID);
	}

	@Override
	public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
			String correlationID) {
		_connectionListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
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
	
	public void startService (SessionType sessionType, byte sessionID, boolean isEncrypted) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.StartProtocolService(sessionType, sessionID, isEncrypted);
			}
		}
	}
	
	public void endService (SessionType sessionType, byte sessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.EndProtocolService(sessionType, sessionID);
			}
		}
	}
	void registerSession(SdlSession registerListener) throws SdlException {
		boolean didAdd = listenerList.addIfAbsent(registerListener);	
		if (!this.getIsConnected()) {
			this.startTransport();
		} else {
			if(didAdd && _transport !=null  && _transport.getTransportType()== TransportType.MULTIPLEX){ //If we're connected we can request the extra session now
				((MultiplexTransport)_transport).requestNewSession();
			}
			this.startHandShake();
		}
	}
	
	public void sendHeartbeat(SdlSession mySession) {
		if(_protocol != null && mySession != null)
			_protocol.SendHeartBeat(mySession.getSessionId());
	}	
	
	public void unregisterSession(SdlSession registerListener) {
		boolean didRemove = listenerList.remove(registerListener);
		if(didRemove && _transport !=null  && _transport.getTransportType()== TransportType.MULTIPLEX){ //If we're connected we can request the extra session now
			((MultiplexTransport)_transport).removeSession(registerListener.getSessionId());
		}		
		closeConnection(listenerList.size() == 0, registerListener.getSessionId(), registerListener.getSessionHashId());
	}

	
	public SdlSession findSessionById(byte id) {
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
			if(cachedMultiConfig!=null ){
				if(cachedMultiConfig.getService()!=null){
					synchronized(TRANSPORT_REFERENCE_LOCK) {
						// Ensure transport is null
						if (_transport != null) {
							if (_transport.getIsConnected()) {
								_transport.disconnect();
							}
							_transport = null;
						}
						_transport = new MultiplexTransport(cachedMultiConfig, SdlConnection.this);
						try {
							startTransport();
						} catch (SdlException e) {
							e.printStackTrace();
						}
					}
				}else{ //The service must be null or already consumed. Let's see if we can find the connection that consumed it
					for (SdlSession session : listenerList) {
						session.checkForOpenMultiplexConnection(SdlConnection.this);;
					}
				}
			}
		}

		@Override
		public void onTransportError(String info, Exception e) {
			//If there's an error with the transport we want to make sure we clear out any reference to it held by the static list in sessions
			SdlSession.removeConnection(SdlConnection.this);
			//If we are erroring out to go into legacy mode, lets cache our multiplexing
			if(isLegacyModeEnabled() && _transport!=null && TransportType.MULTIPLEX.equals(_transport.getTransportType())){
				MultiplexTransport multi = ((MultiplexTransport)_transport);
				cachedMultiConfig = multi.getConfig();
				cachedMultiConfig.setService(null); //Make sure we're clearning this out
			}else{
				cachedMultiConfig = null; //It should now be consumed
			}
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
		public void onProtocolSessionStarted(SessionType sessionType,
				byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted) {
			for (SdlSession session : listenerList) {
				if (session.getSessionId() == 0) {
					session.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
					break;
				}
			}
			
			if (sessionType.equals(SessionType.NAV) || sessionType.equals(SessionType.PCM) || isEncrypted){
				SdlSession session = findSessionById(sessionID);
				if (session != null) {
					session.onProtocolSessionStarted(sessionType, sessionID, version, correlationID, hashID, isEncrypted);
				}
			}
		}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType,
				byte sessionID, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionEnded(sessionType, sessionID, correlationID);
			}
		}

		@Override
		public void onProtocolError(String info, Exception e) {
			for (SdlSession session : listenerList) {
				session.onProtocolError(info, e);
			}
		}

		@Override
		public void onProtocolSessionStartedNACKed(SessionType sessionType,
				byte sessionID, byte version, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionStartedNACKed(sessionType, sessionID, version, correlationID);
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
		public void onProtocolSessionEndedNACKed(SessionType sessionType, byte sessionID, String correlationID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
			}			
			}

		@Override
		public void onProtocolServiceDataACK(SessionType serviceType, int dataSize, byte sessionID) {
			SdlSession session = findSessionById(sessionID);
			if (session != null) {
				session.onProtocolServiceDataACK(serviceType, dataSize, sessionID);
			}
		}			
	}
		
	public int getRegisterCount() {
		return listenerList.size();
	}
	
	@Override
	public void onProtocolHeartbeat(SessionType sessionType, byte sessionID) {
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
    public void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {
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
    public void onResetOutgoingHeartbeat(SessionType sessionType, byte sessionID){
    	
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._outgoingHeartbeatMonitor != null) {
    		mySession._outgoingHeartbeatMonitor.notifyTransportActivity();
        }
    }

    @Override
    public void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID){
    	
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._incomingHeartbeatMonitor != null) {
    		mySession._incomingHeartbeatMonitor.notifyTransportActivity();
        }
    }

	public void forceHardwareConnectEvent(TransportType type){
		if(_transport == null){
			Log.w(TAG, "Unable to force connect, transport was null!");
			return;
		}
		if(isLegacyModeEnabled()){//We know we should no longer be in legacy mode for future connections, so lets clear out that flag
			enableLegacyMode(false,null);	
		}
		if(_transport!=null && (_transport.getTransportType()==TransportType.MULTIPLEX)){ //This is only valid for the multiplex connection
			MultiplexTransport multi = ((MultiplexTransport)_transport);
			MultiplexTransportConfig config = multi.getConfig();
			ComponentName tempCompName = SdlBroadcastReceiver.consumeQueuedRouterService();
			if(config.getService().equals(tempCompName)){ //If this is the same service that just connected that we are already looking at. Attempt to reconnect
				if(!multi.getIsConnected() && multi.isDisconnecting() ){ //If we aren't able to force a connection it means the 
					_transport = new MultiplexTransport(config,this);
					try {
						startTransport();
					} catch (SdlException e) {
						e.printStackTrace();
					}
				}
			}else if(tempCompName!=null){
				//We have a conflicting service request
				Log.w(TAG, "Conflicting services. Disconnecting from current and connecting to new");
				//Log.w(TAG, "Old service " + config.getService().toShortString());
				//Log.w(TAG, "New Serivce " + tempCompName.toString());
				multi.disconnect();
				config.setService(tempCompName);
				_transport = new MultiplexTransport(config,this);
				try {
					startTransport();
				} catch (SdlException e) {
					e.printStackTrace();
				}
				
			}
		}else if(_transport.getTransportType()==TransportType.BLUETOOTH 
				&& !_transport.getIsConnected()){
			if(cachedMultiConfig!=null){
				//We are in legacy mode, but just received a force connect. The router service should never be pointing us here if we are truely in legacy mode
				ComponentName tempCompName = SdlBroadcastReceiver.consumeQueuedRouterService();
				cachedMultiConfig.setService(tempCompName);
				//We are not connected yet so we should be able to close down
				_transport.disconnect(); //This will force us into the 
			}else{
				Log.i(TAG, "No cached multiplexing config, transport error being called");
				_transport.disconnect();
			}
			Log.w(TAG, "Using own transport, but not connected. Attempting to join multiplexing");		
		}else{
			Log.w(TAG, "Currently in legacy mode connected to own transport service. Nothing will take place on trnasport cycle");	
		}
	}
	
	public static void enableLegacyMode(boolean enable, TransportType type){
		synchronized(TRANSPORT_REFERENCE_LOCK) {
			if(enable){
				legacyTransportRequest = type;
			}else{
				legacyTransportRequest = null;
			}
		}
	}
	public static boolean isLegacyModeEnabled(){
		synchronized(TRANSPORT_REFERENCE_LOCK) {
			return (legacyTransportRequest!=null);
		}
	}
    
	@Override
	public void onProtocolSessionEndedNACKed(SessionType sessionType,
			byte sessionID, String correlationID) {
		_connectionListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
		
	}

	@Override
	public void onProtocolServiceDataACK(SessionType serviceType, int dataSize, byte sessionID) {
		_connectionListener.onProtocolServiceDataACK(serviceType, dataSize, sessionID);
	}
}
