package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class AbstractPacketizerTests extends TestCase {
		
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.AbstractPacketizer#AbstractPacketizer(IStreamListener, InputStream, SessionType, byte)}
	 * {@link com.smartdevicelink.streaming.AbstractPacketizer#AbstractPacketizer(IStreamListener, InputStream, RPCRequest, SessionType, byte, byte)}
	 */
	public void testConstructors () {
		
		// Test Values
		byte            testSessionId    = (byte) 0x0A;
		byte            testWiproVersion = (byte) 0x0B;
		RPCRequest      testRpcRequest   = new RPCRequest("test");
		SessionType     testSessionType  = SessionType.RPC;
		SdlSession      testSdlSession  = null;
		InputStream     testInputStream  = null;
		MockPacketizer  testPacketizer1  = null;
		MockPacketizer  testPacketizer2  = null;
		MockPacketizer  testPacketizer3  = null;
		IStreamListener testListener     = new MockStreamListener();
		try {
			testInputStream = new BufferedInputStream(new ByteArrayInputStream("sdl streaming test".getBytes()));
			MockInterfaceBroker _interfaceBroker = new MockInterfaceBroker();
			BaseTransportConfig _transportConfig = new BTTransportConfig(true);
			testSdlSession = SdlSession.createSession(testWiproVersion,_interfaceBroker, _transportConfig);
			testPacketizer1 = new MockPacketizer(testListener, testInputStream, testSessionType, testSessionId, testSdlSession);
			testPacketizer2 = new MockPacketizer(null, null, null, testSessionId, testSdlSession);
			testPacketizer3 = new MockPacketizer(testListener, testInputStream, testRpcRequest, testSessionType, testSessionId, testWiproVersion, testSdlSession);
			try {
				new MockPacketizer(null, null, null, null, testSessionId, testWiproVersion, null);
				fail("Exception should be thrown");
			}catch(Exception e) {
				assertTrue(e instanceof IllegalArgumentException);
			}

			
			// Valid Tests
			assertNotNull(Test.NOT_NULL, testPacketizer1);
			assertNotNull(Test.NOT_NULL, testPacketizer2);
			assertNotNull(Test.NOT_NULL, testPacketizer3);
			
			assertEquals(Test.MATCH, testListener, testPacketizer1.getListener());
			assertEquals(Test.MATCH, testInputStream, testPacketizer1.getInputStream());
			assertEquals(Test.MATCH, testSessionType, testPacketizer1.getSessionType());
			assertEquals(Test.MATCH, testSessionId, testPacketizer1.getSessionId());
			assertEquals(Test.MATCH, testListener, testPacketizer3.getListener());
			assertEquals(Test.MATCH, testInputStream, testPacketizer3.getInputStream());
			assertEquals(Test.MATCH, testSessionType, testPacketizer3.getSessionType());
			assertEquals(Test.MATCH, testSessionId, testPacketizer3.getSessionId());
			assertEquals(Test.MATCH, testWiproVersion, testPacketizer3.getWiproVersion());
			assertEquals(Test.MATCH, testRpcRequest.getFunctionName(), testPacketizer3.getRPCRequest().getFunctionName());
			assertEquals(Test.MATCH, testSdlSession, testPacketizer3.getSdlSession());
						
			// Invalid/Null Tests
			assertNull(Test.NULL, testPacketizer2.getListener());
			assertNull(Test.NULL, testPacketizer2.getInputStream());
			assertNull(Test.NULL, testPacketizer2.getSessionType());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException was thrown.");
		}
	}
}

