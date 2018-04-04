package com.smartdevicelink.SdlConnection;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface ISdlConnectionListener {
	@Deprecated
	void onTransportDisconnected(String info);

	void onTransportDisconnected(String info, TransportType transportType);

	@Deprecated
	void onTransportError(String info, Exception e);

	void onTransportError(String info, TransportType transportType, Exception e);

	void onProtocolMessageReceived(ProtocolMessage msg);
	
	void onProtocolSessionStartedNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID, List<String> rejectedParams);
	
	@Deprecated
	void onProtocolSessionStarted(SessionType sessionType,
	        byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted);

	void onProtocolSessionStarted(SessionType sessionType, byte sessionID, byte version,
	        String correlationID, int hashID, boolean isEncrypted, TransportType transportType);

	@Deprecated
	void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID);

	void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID, TransportType transportType);
	
	void onProtocolSessionEndedNACKed(SessionType sessionType,
			byte sessionID, String correlationID);
	
	void onProtocolError(String info, Exception e);
	
	void onHeartbeatTimedOut(byte sessionID);
	
	void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID);

	void onEnableSecondaryTransport(byte sessionID, ArrayList<String> secondaryTransports,
	        ArrayList<Integer> audioTransports, ArrayList<Integer> videoTransports,
	        TransportType type);

	void onTransportEventUpdate(byte sessionID, Map<String, Object> params);

	void onRegisterSecondaryTransportACK(byte sessionID);

	void onRegisterSecondaryTransportNACKed(byte sessionID, String reason);
}
