package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;
import com.smartdevicelink.util.Version;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static androidx.test.platform.app.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
public class AbstractPacketizerTests extends TestCase {
		
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.AbstractPacketizer#AbstractPacketizer(IStreamListener, InputStream, RPCRequest, SessionType, byte, Version, SdlSession)} 
	 * {@link com.smartdevicelink.streaming.AbstractPacketizer#AbstractPacketizer(IStreamListener, InputStream, RPCRequest, SessionType, byte, Version, SdlSession)}
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
			MockInterfaceBroker interfaceBroker = new MockInterfaceBroker();
			MultiplexTransportConfig transportConfig = new MultiplexTransportConfig(getTargetContext(),"19216801");
			testSdlSession = new SdlSession(interfaceBroker, transportConfig);
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
			assertNotNull(TestValues.NOT_NULL, testPacketizer1);
			assertNotNull(TestValues.NOT_NULL, testPacketizer2);
			assertNotNull(TestValues.NOT_NULL, testPacketizer3);
			
			assertEquals(TestValues.MATCH, testListener, testPacketizer1.getListener());
			assertEquals(TestValues.MATCH, testInputStream, testPacketizer1.getInputStream());
			assertEquals(TestValues.MATCH, testSessionType, testPacketizer1.getSessionType());
			assertEquals(TestValues.MATCH, testSessionId, testPacketizer1.getSessionId());
			assertEquals(TestValues.MATCH, testListener, testPacketizer3.getListener());
			assertEquals(TestValues.MATCH, testInputStream, testPacketizer3.getInputStream());
			assertEquals(TestValues.MATCH, testSessionType, testPacketizer3.getSessionType());
			assertEquals(TestValues.MATCH, testSessionId, testPacketizer3.getSessionId());
			assertEquals(TestValues.MATCH, testWiproVersion, testPacketizer3.getWiproVersion());
			assertEquals(TestValues.MATCH, testRpcRequest.getFunctionName(), testPacketizer3.getRPCRequest().getFunctionName());
			assertEquals(TestValues.MATCH, testSdlSession, testPacketizer3.getSdlSession());
						
			// Invalid/Null Tests
			assertNull(TestValues.NULL, testPacketizer2.getListener());
			assertNull(TestValues.NULL, testPacketizer2.getInputStream());
			assertNull(TestValues.NULL, testPacketizer2.getSessionType());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail("IOException was thrown.");
		}
	}
}

