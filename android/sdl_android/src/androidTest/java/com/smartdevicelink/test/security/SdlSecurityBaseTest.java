package com.smartdevicelink.test.security;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.security.SdlSecurityBase;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.streaming.MockInterfaceBroker;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
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
		MultiplexTransportConfig transportConfig = new MultiplexTransportConfig(getInstrumentation().getTargetContext(),"19216801");
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
