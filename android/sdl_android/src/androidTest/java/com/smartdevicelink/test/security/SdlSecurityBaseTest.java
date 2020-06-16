package com.smartdevicelink.test.security;

import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.SdlConnection.ISdlConnectionListener;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class SdlSecurityBaseTest {

	private class MockSdlSecurityBase extends SdlSecurityBase {

		@Override
		public void initialize() {
			
		}

		@Override
		public Integer runHandshake(byte[] inputData, byte[] outputData) {
			return null;
		}

		@Override
		public Integer encryptData(byte[] inputData, byte[] outputData) {
			return null;
		}

		@Override
		public Integer decryptData(byte[] inputData, byte[] outputData) {
			return null;
		}

		@Override
		public void shutDown() {
			
		}
		
	    public SdlSession getSdlSession() {
	    	return session;
	    }
	    
	    public void setStartServiceList(List<SessionType> list) {
	    	startServiceList = list;
	    }
	}
	
	class MockInterfaceBroker implements ISdlConnectionListener {
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
		@Override
		public void onAuthTokenReceived(String token, byte bytes){}

	}

	@org.junit.Test
	public void testMakeListSetAndGet(){
		List<String> makeList = new ArrayList<String>();
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		String MAKE_1 = "SDL1";
		String MAKE_2 = "SDL2";
    	makeList.add(MAKE_1);
    	makeList.add(MAKE_2);

    	mockSdlSecurityBase.setMakeList(makeList);
    	
    	assertNotNull(Test.NOT_NULL, makeList);
    	assertEquals(Test.MATCH, makeList, mockSdlSecurityBase.getMakeList());
	}

	@org.junit.Test
	public void testHandleInitResult() {
		byte testWiproVersion = (byte) 0x0B;
		boolean testInitResult = true;
		MockInterfaceBroker interfaceBroker = new MockInterfaceBroker();
		BaseTransportConfig transportConfig = new BTTransportConfig(true);
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		SdlSession testSdlSession = SdlSession.createSession(testWiproVersion,interfaceBroker, transportConfig);
		
		assertNotNull(Test.NOT_NULL, mockSdlSecurityBase);
		assertNotNull(Test.NOT_NULL, testSdlSession);
		
		testSdlSession.setSdlSecurity(mockSdlSecurityBase);

		mockSdlSecurityBase.handleSdlSession(testSdlSession);
		
		assertEquals(Test.MATCH, mockSdlSecurityBase.getSdlSession(), testSdlSession);
		assertEquals(Test.MATCH, mockSdlSecurityBase.getSdlSession().getSessionId(), testSdlSession.getSessionId());
		
		mockSdlSecurityBase.handleInitResult(testInitResult);
		
		assertEquals(Test.MATCH, testInitResult, mockSdlSecurityBase.getInitSuccess());
	}

	@org.junit.Test
	public void testStartServiceListSetAndGet() {
		List<SessionType> startServiceList = new ArrayList<SessionType>();
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		startServiceList.add(SessionType.RPC);
		startServiceList.add(SessionType.NAV);
		startServiceList.add(SessionType.PCM);
		startServiceList.add(SessionType.CONTROL);

    	assertNotNull(Test.NOT_NULL, startServiceList);
    	
    	mockSdlSecurityBase.setStartServiceList(startServiceList);
    	
    	assertEquals(Test.MATCH, startServiceList, mockSdlSecurityBase.getServiceList());		
	}

}
