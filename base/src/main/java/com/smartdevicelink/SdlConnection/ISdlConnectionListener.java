package com.smartdevicelink.SdlConnection;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.List;


public interface ISdlConnectionListener {
	@Deprecated
	void onTransportDisconnected(String info);

	void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig);


	void onTransportError(String info, Exception e);
	
	void onProtocolMessageReceived(ProtocolMessage msg);
	
	void onProtocolSessionStartedNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID, List<String> rejectedParams);
	
	void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted);
	
	void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID);
	
	void onProtocolSessionEndedNACKed(SessionType sessionType,
	byte sessionID, String correlationID);
	
	void onProtocolError(String info, Exception e);

	@Deprecated
	void onHeartbeatTimedOut(byte sessionID);
	
	void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID);

	void onAuthTokenReceived(String authToken, byte sessionID);
}
