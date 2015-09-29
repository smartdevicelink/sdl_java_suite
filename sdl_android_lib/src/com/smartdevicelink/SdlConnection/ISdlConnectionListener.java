package com.smartdevicelink.SdlConnection;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.ServiceType;


public interface ISdlConnectionListener {
	public void onTransportDisconnected(String info);
	
	public void onTransportError(String info, Exception e);
	
	public void onProtocolMessageReceived(ProtocolMessage msg);
	
	public void onProtocolSessionStartedNACKed(ServiceType serviceType,
			byte sessionID, byte version, String correlationID);	
	
	public void onProtocolSessionStarted(ServiceType serviceType,
			byte sessionID, byte version, String correlationID);
	
	public void onProtocolSessionEnded(ServiceType serviceType,
			byte sessionID, String correlationID);
	
	public void onProtocolSessionEndedNACKed(ServiceType serviceType,
	byte sessionID, String correlationID);
	
	public void onProtocolError(String info, Exception e);
	
	public void onHeartbeatTimedOut(byte sessionID);
	
	public void onProtocolServiceDataACK(ServiceType serviceType, byte sessionID);
}
