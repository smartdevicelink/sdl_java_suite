package com.smartdevicelink.test.security;

import android.support.test.runner.AndroidJUnit4;

import com.smartdevicelink.SdlConnection.ISdlSessionListener;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
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
	
	class MockInterfaceBroker implements ISdlSessionListener {
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

	@Test
	public void testMakeListSetAndGet(){
		List<String> makeList = new ArrayList<String>();
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		String MAKE_1 = "SDL1";
		String MAKE_2 = "SDL2";
    	makeList.add(MAKE_1);
    	makeList.add(MAKE_2);

    	mockSdlSecurityBase.setMakeList(makeList);
    	
    	assertNotNull(TestValues.NOT_NULL, makeList);
    	assertEquals(TestValues.MATCH, makeList, mockSdlSecurityBase.getMakeList());
	}

	@Test
	public void testHandleInitResult() {
		byte testWiproVersion = (byte) 0x0B;
		boolean testInitResult = true;
		MockInterfaceBroker interfaceBroker = new MockInterfaceBroker();
		MultiplexTransportConfig transportConfig = new MultiplexTransportConfig(getTargetContext(),"19216801");
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		SdlSession testSdlSession = new SdlSession(interfaceBroker, transportConfig);

		assertNotNull(TestValues.NOT_NULL, mockSdlSecurityBase);
		assertNotNull(TestValues.NOT_NULL, testSdlSession);
		
		testSdlSession.setSdlSecurity(mockSdlSecurityBase);

		mockSdlSecurityBase.handleSdlSession(testSdlSession);
		
		assertEquals(TestValues.MATCH, mockSdlSecurityBase.getSdlSession(), testSdlSession);
		assertEquals(TestValues.MATCH, mockSdlSecurityBase.getSdlSession().getSessionId(), testSdlSession.getSessionId());
		
		mockSdlSecurityBase.handleInitResult(testInitResult);
		
		assertEquals(TestValues.MATCH, testInitResult, mockSdlSecurityBase.getInitSuccess());
	}

	@Test
	public void testStartServiceListSetAndGet() {
		List<SessionType> startServiceList = new ArrayList<SessionType>();
		MockSdlSecurityBase mockSdlSecurityBase = new MockSdlSecurityBase();
		
		startServiceList.add(SessionType.RPC);
		startServiceList.add(SessionType.NAV);
		startServiceList.add(SessionType.PCM);
		startServiceList.add(SessionType.CONTROL);

    	assertNotNull(TestValues.NOT_NULL, startServiceList);
    	
    	mockSdlSecurityBase.setStartServiceList(startServiceList);
    	
    	assertEquals(TestValues.MATCH, startServiceList, mockSdlSecurityBase.getServiceList());
	}

}
