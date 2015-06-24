package com.smartdevicelink.SdlConnection;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

import android.util.Log;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.IProtocolListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.transport.*;
import com.smartdevicelink.transport.enums.TransportType;

public class SdlConnection implements IProtocolListener, ITransportListener, IStreamListener  {

	SdlTransport _transport = null;
	AbstractProtocol _protocol = null;
	ISdlConnectionListener _connectionListener = null;
	AbstractPacketizer mPacketizer = null;

	// Thread safety locks
	Object TRANSPORT_REFERENCE_LOCK = new Object();
	Object PROTOCOL_REFERENCE_LOCK = new Object();
	
	private CopyOnWriteArrayList<SdlSession> listenerList = new CopyOnWriteArrayList<SdlSession>();
	
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
					_protocol.EndProtocolSession(SessionType.RPC, rpcSessionID);
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
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		_connectionListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID);
	}

	@Override
	public void onProtocolSessionNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		_connectionListener.onProtocolSessionNACKed(sessionType, sessionID, version, correlationID);
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
	public void startStream(InputStream is, SessionType sType, byte rpcSessionID) {
		try {
            mPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
			mPacketizer.start();
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
	}
	
	public OutputStream startStream(SessionType sType, byte rpcSessionID) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mPacketizer = new StreamPacketizer(this, is, sType, rpcSessionID);
			mPacketizer.start();
			return os;
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
		return null;
	}
		
	public void startRPCStream(InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
		try {
            mPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0);
			mPacketizer.start();
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
	}
	
	public OutputStream startRPCStream(RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mPacketizer = new StreamRPCPacketizer(null, this, is, request, sType, rpcSessionID, wiproVersion, 0);
			mPacketizer.start();
			return os;
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
		return null;
	}

	public void pauseRPCStream()
	{
		if (mPacketizer != null)
		{
			mPacketizer.pause();
		}
	}

	public void resumeRPCStream()
	{
		if (mPacketizer != null)
		{
			mPacketizer.resume();
		}
	}

	public void stopRPCStream()
	{
		if (mPacketizer != null)
		{
			mPacketizer.stop();
		}
	}
	
	@Override
	public void sendStreamPacket(ProtocolMessage pm) {
		sendMessage(pm);
	}
	
	public void startService (SessionType sessionType, byte sessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.StartProtocolService(sessionType, sessionID);
			}
		}
	}
	
	public void endService (SessionType sessionType, byte sessionID) {
		synchronized(PROTOCOL_REFERENCE_LOCK){
			if(_protocol != null){
				_protocol.EndProtocolSession(sessionType, sessionID);
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
		public void onProtocolSessionStarted(SessionType sessionType,
				byte sessionID, byte version, String correlationID) {
			for (SdlSession session : listenerList) {
				if (session.getSessionId() == 0 || sessionType == SessionType.NAV) {
					session.onProtocolSessionStarted(sessionType, sessionID, version, correlationID);
					break; //FIXME: need changes on SDL side, as the sessionID is devided by SDL.
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
		public void onProtocolSessionNACKed(SessionType sessionType,
				byte sessionID, byte version, String correlationID) {
			for (SdlSession session : listenerList) {
				session.onProtocolSessionNACKed(sessionType, sessionID, version, correlationID);
			}			
		}

		@Override
		public void onHeartbeatTimedOut(byte sessionID) {
			for (SdlSession session : listenerList) {
				session.onHeartbeatTimedOut(sessionID);
			}	
			
		}
	}
		
	public int getRegisterCount() {
		return listenerList.size();
	}

    @Override
    public void onProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {
        
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._heartbeatMonitor != null) {
    		mySession._heartbeatMonitor.heartbeatACKReceived();
        }
    }

    @Override
    public void onResetHeartbeat(SessionType sessionType, byte sessionID){
    	
    	SdlSession mySession = findSessionById(sessionID);
    	if (mySession == null) return;
    	
    	if (mySession._heartbeatMonitor != null) {
    		mySession._heartbeatMonitor.notifyTransportActivity();
        }
    }
}
