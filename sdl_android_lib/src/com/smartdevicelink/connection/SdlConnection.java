package com.smartdevicelink.connection;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

import android.util.Log;

import com.smartdevicelink.connection.interfaces.ISdlConnectionListener;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.AbstractProtocol;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.WiProProtocol;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.protocol.interfaces.IProtocolListener;
import com.smartdevicelink.proxy.RpcRequest;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.streaming.StreamRpcPacketizer;
import com.smartdevicelink.streaming.interfaces.IStreamListener;
import com.smartdevicelink.transport.*;
import com.smartdevicelink.transport.interfaces.ITransportListener;

public class SdlConnection implements IProtocolListener, ITransportListener, IStreamListener  {

	SdlTransport transport = null;
	AbstractProtocol protocol = null;
	ISdlConnectionListener connectionListener = null;
	AbstractPacketizer mPacketizer = null;

	// Thread safety locks
	Object transportReferenceLock = new Object();
	Object protocolReferenceLock = new Object();
	
	private CopyOnWriteArrayList<SdlSession> listenerList = new CopyOnWriteArrayList<SdlSession>();
	
	/**
	 * Constructor.
	 * 
	 * @param listener Sdl connection listener.
	 * @param transportConfig Transport configuration for this connection.
	 */
	public SdlConnection(BaseTransportConfig transportConfig) {
		connectionListener = new InternalMsgDispatcher();
		
		// Initialize the transport
		synchronized(transportReferenceLock) {
			// Ensure transport is null
			if (transport != null) {
				if (transport.getIsConnected()) {
					transport.disconnect();
				}
				transport = null;
			}
			
			if (transportConfig.getTransportType() == TransportType.BLUETOOTH)
			{				
				BtTransportConfig myConfig = (BtTransportConfig) transportConfig;				
				transport = new BtTransport(this, myConfig.getKeepSocketActive());
			}
			else if (transportConfig.getTransportType() == TransportType.TCP)
			{
                transport = new TcpTransport((TcpTransportConfig) transportConfig, this);
            } else if (transportConfig.getTransportType() == TransportType.USB) {
                transport = new UsbTransport((UsbTransportConfig) transportConfig, this);
            }
		}
		
		// Initialize the protocol
		synchronized(protocolReferenceLock) {
			// Ensure protocol is null
			if (protocol != null) {
				protocol = null;
			}
			
			protocol = new WiProProtocol(this);
		}
	}
	
	public AbstractProtocol getWiProProtocol(){
		return protocol;
	}
	

	
	
	private void closeConnection(boolean willRecycle, byte rpcSessionId) {
		synchronized(protocolReferenceLock) {
			if (protocol != null) {
				// If transport is still connected, sent EndProtocolSessionMessage
				if (transport != null && transport.getIsConnected()) {
					protocol.endProtocolSession(SessionType.RPC, rpcSessionId);
				}
				if (willRecycle) {
				protocol = null;
				}
			} // end-if
		}
		
		synchronized (transportReferenceLock) {
			if (willRecycle) {
			if (transport != null) {
				transport.disconnect();
			}
			transport = null;
		}
	}
	}
		
	
	public void startTransport() throws SdlException {
		transport.openConnection();
	}
	
	public Boolean getIsConnected() {
		
		// If _transport is null, then it can't be connected
		if (transport == null) {
			return false;
		}
		
		return transport.getIsConnected();
	}
	
	public String getBroadcastComment() {
		
		if (transport == null) {
			return "";
		}
		
		return transport.getBroadcastComment();
	}
	
	public void sendMessage(ProtocolMessage msg) {
		if(protocol != null)
			protocol.sendMessage(msg);
	}
	
	void startHandShake() {
		synchronized(protocolReferenceLock){
			if(protocol != null){
				protocol.startProtocolSession(SessionType.RPC);
			}
		}
	}	
	
	@Override
	public void onTransportBytesReceived(byte[] receivedBytes,
			int receivedBytesLength) {
		// Send bytes to protocol to be interpreted 
		synchronized(protocolReferenceLock) {
			if (protocol != null) {
				protocol.handleReceivedBytes(receivedBytes, receivedBytesLength);
			}
		}
	}

	@Override
	public void onTransportConnected() {
		synchronized(protocolReferenceLock){
			if(protocol != null){
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
		connectionListener.onTransportDisconnected(info);
	}

	@Override
	public void onTransportError(String info, Exception e) {
		// Pass directly to connection listener
		connectionListener.onTransportError(info, e);
	}

	@Override
	public void onProtocolMessageBytesToSend(byte[] msgBytes, int offset,
			int length) {
		// Protocol has packaged bytes to send, pass to transport for transmission 
		synchronized(transportReferenceLock) {
			if (transport != null) {
				transport.sendBytes(msgBytes, offset, length);
			}
		}
	}
	
	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {
		connectionListener.onProtocolMessageReceived(msg);
	}

	@Override
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionId, byte version, String correlationId) {
		connectionListener.onProtocolSessionStarted(sessionType, sessionId, version, correlationId);
	}

	@Override
	public void onProtocolSessionNack(SessionType sessionType,
			byte sessionId, byte version, String correlationId) {
		connectionListener.onProtocolSessionNack(sessionType, sessionId, version, correlationId);
	}

	@Override
	public void onProtocolSessionEnded(SessionType sessionType, byte sessionId,
			String correlationId) {
		connectionListener.onProtocolSessionEnded(sessionType, sessionId, correlationId);
	}

	@Override
	public void onProtocolError(String info, Exception e) {
		connectionListener.onProtocolError(info, e);
	}
	
	/**
	 * Gets type of transport currently used by this connection.
	 * 
	 * @return One of TransportType enumeration values.
	 * 
	 * @see TransportType
	 */
	public TransportType getCurrentTransportType() {
		return transport.getTransportType();
	}
	public void startStream(InputStream is, SessionType sType, byte rpcSessionId) {
		try {
            mPacketizer = new StreamPacketizer(this, is, sType, rpcSessionId);
			mPacketizer.start();
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
	}
	
	public OutputStream startStream(SessionType sType, byte rpcSessionId) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mPacketizer = new StreamPacketizer(this, is, sType, rpcSessionId);
			mPacketizer.start();
			return os;
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
		return null;
	}
		
	public void startRpcStream(InputStream is, RpcRequest request, SessionType sType, byte rpcSessionId, byte wiproVersion) {
		try {
            mPacketizer = new StreamRpcPacketizer(null, this, is, request, sType, rpcSessionId, wiproVersion, 0);
			mPacketizer.start();
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
	}
	
	public OutputStream startRpcStream(RpcRequest request, SessionType sType, byte rpcSessionId, byte wiproVersion) {
		try {
			OutputStream os = new PipedOutputStream();
	        InputStream is = new PipedInputStream((PipedOutputStream) os);
			mPacketizer = new StreamRpcPacketizer(null, this, is, request, sType, rpcSessionId, wiproVersion, 0);
			mPacketizer.start();
			return os;
		} catch (Exception e) {
            Log.e("SdlConnection", "Unable to start streaming:" + e.toString());
        }
		return null;
	}

	public void pauseRpcStream()
	{
		if (mPacketizer != null)
		{
			mPacketizer.pause();
		}
	}

	public void resumeRpcStream()
	{
		if (mPacketizer != null)
		{
			mPacketizer.resume();
		}
	}

	public void stopRpcStream()
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
	
	public void startService (SessionType sessionType, byte sessionId) {
		synchronized(protocolReferenceLock){
			if(protocol != null){
				protocol.startProtocolService(sessionType, sessionId);
			}
		}
	}
	
	public void endService (SessionType sessionType, byte sessionId) {
		synchronized(protocolReferenceLock){
			if(protocol != null){
				protocol.endProtocolSession(sessionType, sessionId);
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
		if(protocol != null && mySession != null)
			protocol.sendHeartBeat(mySession.getSessionId());
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
			SdlSession session = findSessionById(msg.getSessionId());
			if (session != null) {
				session.onProtocolMessageReceived(msg);
			}
		}

		@Override
		public void onProtocolSessionStarted(SessionType sessionType,
				byte sessionId, byte version, String correlationId) {
			for (SdlSession session : listenerList) {
				if (session.getSessionId() == 0 || sessionType == SessionType.NAV) {
					session.onProtocolSessionStarted(sessionType, sessionId, version, correlationId);
					break; //FIXME: need changes on SDL side, as the sessionID is devided by SDL.
				}
			}
		}

		@Override
		public void onProtocolSessionEnded(SessionType sessionType,
				byte sessionId, String correlationId) {
			SdlSession session = findSessionById(sessionId);
			if (session != null) {
				session.onProtocolSessionEnded(sessionType, sessionId, correlationId);
			}
		}

		@Override
		public void onProtocolError(String info, Exception e) {
			for (SdlSession session : listenerList) {
				session.onProtocolError(info, e);
			}
		}

		@Override
		public void onProtocolSessionNack(SessionType sessionType,
				byte sessionID, byte version, String correlationID) {
			for (SdlSession session : listenerList) {
				session.onProtocolSessionNack(sessionType, sessionID, version, correlationID);
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
    public void onProtocolHeartbeatAck(SessionType sessionType, byte sessionId) {
        
    	SdlSession mySession = findSessionById(sessionId);
    	if (mySession == null) return;
    	
    	if (mySession.heartbeatMonitor != null) {
    		mySession.heartbeatMonitor.heartbeatAckReceived();
        }
    }

    @Override
    public void onResetHeartbeat(SessionType sessionType, byte sessionId){
    	
    	SdlSession mySession = findSessionById(sessionId);
    	if (mySession == null) return;
    	
    	if (mySession.heartbeatMonitor != null) {
    		mySession.heartbeatMonitor.notifyTransportActivity();
        }
    }
}
