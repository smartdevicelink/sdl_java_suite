package com.smartdevicelink.SdlConnection;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface ISdlConnectionListener {
	@Deprecated
	public void onTransportDisconnected(String info);

	public void onTransportDisconnected(String info, boolean availablePrimary, MultiplexTransportConfig transportConfig);


	public void onTransportError(String info, Exception e);
	
	public void onProtocolMessageReceived(ProtocolMessage msg);
	
	public void onProtocolSessionStartedNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID, List<String> rejectedParams);
	
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID, int hashID, boolean isEncrypted);
	
	public void onProtocolSessionEnded(SessionType sessionType,
			byte sessionID, String correlationID);
	
	public void onProtocolSessionEndedNACKed(SessionType sessionType,
	byte sessionID, String correlationID);
	
	public void onProtocolError(String info, Exception e);
	
	public void onHeartbeatTimedOut(byte sessionID);
	
	public void onProtocolServiceDataACK(SessionType sessionType, int dataSize, byte sessionID);
}
