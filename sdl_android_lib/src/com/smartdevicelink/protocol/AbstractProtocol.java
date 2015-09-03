package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.WiProProtocol.MessageFrameAssembler;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.trace.SdlTrace;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;

public abstract class AbstractProtocol {
	private static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";
	
	private IProtocolListener _protocolListener = null;
	//protected IProtocolListener ProtocolListener() { return _protocolListener; }
	
	// Lock to ensure all frames are sent uninterupted 
	private Object _frameLock = new Object();

	// Caller must provide a non-null IProtocolListener interface reference.
	public AbstractProtocol(IProtocolListener protocolListener) {
		if (protocolListener == null) {
    		throw new IllegalArgumentException("Provided protocol listener interface reference is null");
		} // end-if
		
		_protocolListener = protocolListener;
	} // end-ctor

	// This method receives raw bytes as they arrive from transport.  Those bytes
	// are then collected by the protocol and assembled into complete messages and
	// handled internally by the protocol or propagated to the protocol listener.
	public abstract void HandleReceivedBytes(byte[] receivedBytes, int length);

	// This method receives a protocol message (e.g. RPC, BULK, etc.) and processes
	// it for transmission over the transport.  The results of this processing will
	// be sent to the onProtocolMessageBytesToSend() method on protocol listener
	// interface.  Note that the ProtocolMessage itself contains information
	// about the type of message (e.g. RPC, BULK, etc.) and the protocol session
	// over which to send the message, etc.
	public abstract void SendMessage(ProtocolMessage msg);

	// This method starts a protocol session.  A corresponding call to the protocol
	// listener onProtocolSessionStarted() method will be made when the protocol
	// session has been established.
	public abstract void StartProtocolSession(SessionType sessionType);
	
	public abstract void StartProtocolService(SessionType sessionType, byte sessionID);

	// This method ends a protocol session.  A corresponding call to the protocol
	// listener onProtocolSessionEnded() method will be made when the protocol
	// session has ended.
	public abstract void EndProtocolSession(SessionType sessionType, byte sessionID);
    // TODO REMOVE
    // This method sets the interval at which heartbeat protocol messages will be
    // sent to SDL.
    public abstract void SetHeartbeatSendInterval(int heartbeatSendInterval_ms);

    // This method sets the interval at which heartbeat protocol messages are
    // expected to be received from SDL.
    public abstract void SetHeartbeatReceiveInterval(int heartbeatReceiveInterval_ms);
    
    public abstract void SendHeartBeat(byte sessionID);

	public abstract void SendHeartBeatACK(byte sessionID);
	
	// This method is called whenever the protocol receives a complete frame
	protected void handleProtocolFrameReceived(ProtocolFrameHeader header, byte[] data, MessageFrameAssembler assembler) {
		SdlTrace.logProtocolEvent(InterfaceActivityDirection.Receive, header, data, 
				0, data.length, SDL_LIB_TRACE_KEY);
		
		assembler.handleFrame(header, data);
	}
	
    private synchronized void resetOutgoingHeartbeat(SessionType sessionType, byte sessionID) {
        if (_protocolListener != null) {
            _protocolListener.onResetOutgoingHeartbeat(sessionType,sessionID);
        }
    }
	
    private synchronized void resetIncomingHeartbeat(SessionType sessionType, byte sessionID) {
        if (_protocolListener != null) {
            _protocolListener.onResetIncomingHeartbeat(sessionType,sessionID);
        }
    }

	// This method is called whenever a protocol has an entire frame to send
	protected void handleProtocolFrameToSend(ProtocolFrameHeader header, byte[] data, int offset, int length) {
		SdlTrace.logProtocolEvent(InterfaceActivityDirection.Transmit, header, data, 
				offset, length, SDL_LIB_TRACE_KEY);
		resetOutgoingHeartbeat(header.getSessionType(), header.getSessionID());
		synchronized(_frameLock) {
			byte[] frameHeader = header.assembleHeaderBytes();
			handleProtocolMessageBytesToSend(frameHeader, 0, frameHeader.length);
			
			if (data != null) {
				handleProtocolMessageBytesToSend(data, offset, length);
			} // end-if
		}
	}
	
	// This method handles protocol message bytes that are ready to send.
	// A callback is sent to the protocol listener.
	protected void handleProtocolMessageBytesToSend(byte[] bytesToSend,
			int offset, int length) {
		_protocolListener.onProtocolMessageBytesToSend(bytesToSend, offset, length);
	}
	
	// This method handles received protocol messages. 
	protected void handleProtocolMessageReceived(ProtocolMessage message) {
		_protocolListener.onProtocolMessageReceived(message);
	}
	
	// This method handles the end of a protocol session. A callback is 
	// sent to the protocol listener.
	protected void handleProtocolSessionEndedNACK(SessionType sessionType,
			byte sessionID, String correlationID) {
		_protocolListener.onProtocolSessionEndedNACKed(sessionType, sessionID, correlationID);
	}	
	
	// This method handles the end of a protocol session. A callback is 
	// sent to the protocol listener.
	protected void handleProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID) {
		_protocolListener.onProtocolSessionEnded(sessionType, sessionID, correlationID);
	}
	
	// This method handles the startup of a protocol session. A callback is sent
	// to the protocol listener.
	protected void handleProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		_protocolListener.onProtocolSessionStarted(sessionType, sessionID, version, correlationID);
	}

	protected void handleProtocolSessionNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		_protocolListener.onProtocolSessionNACKed(sessionType, sessionID, version, correlationID);
	}
	
	// This method handles protocol errors. A callback is sent to the protocol
	// listener.
	protected void handleProtocolError(String string, Exception ex) {
		_protocolListener.onProtocolError(string, ex);
	}
    protected void handleProtocolHeartbeat(SessionType sessionType, byte sessionID) {
    	SendHeartBeatACK(sessionID);
    	_protocolListener.onProtocolHeartbeat(sessionType, sessionID);
    }
    protected void handleProtocolHeartbeatACK(SessionType sessionType, byte sessionID) {
        _protocolListener.onProtocolHeartbeatACK(sessionType, sessionID);
    }
    protected void handleProtocolServiceDataACK(SessionType sessionType, byte sessionID) {
        _protocolListener.onProtocolServiceDataACK(sessionType, sessionID);
    }
    protected void onResetIncomingHeartbeat(SessionType sessionType, byte sessionID) {
		resetIncomingHeartbeat(sessionType, sessionID);
    }
} // end-class
