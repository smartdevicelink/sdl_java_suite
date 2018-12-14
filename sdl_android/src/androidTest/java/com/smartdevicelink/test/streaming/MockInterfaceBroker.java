package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.ISdlConnectionListener;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.transport.enums.TransportType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is a mock class for testing the following :
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class MockInterfaceBroker implements ISdlConnectionListener {
	public MockInterfaceBroker () { }
	@Override
	public void onTransportDisconnected(String info) {

	}

	@Override
	public void onTransportDisconnected(String info, boolean availablePrimary, MultiplexTransportConfig transportConfig) {

	}

	@Override
	public void onTransportError(String info, Exception e) {

	}
	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {

	}
	@Override
	public void onProtocolSessionStartedNACKed(SessionType sessionType,
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
}
