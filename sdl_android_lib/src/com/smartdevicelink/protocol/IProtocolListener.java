package com.smartdevicelink.protocol;

import com.smartdevicelink.protocol.enums.*;

public interface IProtocolListener {
	// Called to indicate that these bytes are to be sent as part of a message.
	// This call includes the part of the message.
	void onProtocolMessageBytesToSend(byte[] msgBytes, int offset, int length);

	// Called to indicate that a complete message (RPC, BULK, etc.) has been
	// received.  This call includes the message.
	void onProtocolMessageReceived(ProtocolMessage msg);

	// Called to indicate that a protocol session has been started (from either side)
	void onProtocolSessionStarted(ServiceType serviceType, byte sessionID, byte version, String correlationID);
	
	void onProtocolSessionNACKed(ServiceType serviceType, byte sessionID, byte version, String correlationID);	

	// Called to indicate that a protocol session has ended (from either side)
	void onProtocolSessionEnded(ServiceType serviceType, byte sessionID, String correlationID /*, String info, Exception ex*/);
 	
	void onProtocolSessionEndedNACKed(ServiceType serviceType, byte sessionID, String correlationID /*, String info, Exception ex*/);

	void onProtocolHeartbeat(ServiceType serviceType, byte sessionID);
	
	/**
     * Called when a protocol heartbeat ACK message has been received from SDL.
     */
    void onProtocolHeartbeatACK(ServiceType serviceType, byte sessionID);
    
    void onProtocolServiceDataACK(ServiceType serviceType, byte sessionID);

    void onResetOutgoingHeartbeat(ServiceType serviceType, byte sessionID);

    void onResetIncomingHeartbeat(ServiceType serviceType, byte sessionID);

	// Called to indicate that a protocol error was detected in received data.
	void onProtocolError(String info, Exception e);
} // end-interfCe
