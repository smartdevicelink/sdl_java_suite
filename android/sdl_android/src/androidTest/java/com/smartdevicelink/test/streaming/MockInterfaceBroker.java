package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.ISdlSessionListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.BaseTransportConfig;

import java.util.List;

/**
 * This is a mock class for testing the following :
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class MockInterfaceBroker implements ISdlSessionListener {
	public MockInterfaceBroker () { }
	@Override
	public void onTransportDisconnected(String info) {

	}

	@Override
	public void onTransportDisconnected(String info, boolean availablePrimary, BaseTransportConfig transportConfig) {

	}

	@Override
	public void onTransportError(String info, Exception e) {

	}
	@Override
	public void onRPCReceived(ProtocolMessage msg) {

	}
	@Override
	public void onStartSessionNAK(SessionType sessionType,
								  byte sessionID, byte version, String correlationID, List<String> rejectedParams) {

	}
	@Override
	public void onProtocolSessionStarted(SessionType sessionType,
			byte sessionID, byte version, String correlationID, int hashID,
			boolean isEncrypted) {

	}
	@Override
	public void onProtocolSessionEnded(SessionType sessionType, byte sessionID,
			String correlationID) {

	}
	@Override
	public void onProtocolSessionEndedNACKed(SessionType sessionType,
			byte sessionID, String correlationID) {

	}
	@Override
	public void onProtocolError(String info, Exception e) {

	}
	@Override
	public void onHeartbeatTimedOut(byte sessionID) {

	}
	@Override
	public void onProtocolServiceDataACK(SessionType sessionType, int dataSize,
			byte sessionID) {

	}
	@Override
	public void onAuthTokenReceived(String token, byte bytes){}
}
