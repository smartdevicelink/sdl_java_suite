package com.smartdevicelink.test.streaming;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.SdlProxyBase;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamRPCPacketizer;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;
import com.smartdevicelink.transport.MultiplexTransportConfig;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.support.test.InstrumentationRegistry.getTargetContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.StreamRPCPacketizer}
 */
public class StreamRPCPacketizerTests extends TestCase {
		
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.StreamRPCPacketizer#StreamRPCPacketizer(SdlProxyBase, IStreamListener, InputStream, RPCRequest, SessionType, byte, byte, long, SdlSession)}
	 */
	public void testConstructor () {
		
		// Test Values
		byte testSessionId           = (byte) 0x0A;
		byte testWV                  = (byte) 0x0B;
		RPCRequest  testRequest      = new RPCRequest("test");
		SessionType testSessionType  = SessionType.RPC;
		InputStream testInputStream  = null;
		IStreamListener testListener = new MockStreamListener();
		
		MockInterfaceBroker interfaceBroker = new MockInterfaceBroker();
		MultiplexTransportConfig transportConfig = new MultiplexTransportConfig(getTargetContext(),"19216801");
		SdlSession testSdlSession = new SdlSession(interfaceBroker, transportConfig);
		try {
			testInputStream = new BufferedInputStream(new ByteArrayInputStream("sdl streaming test".getBytes()));
			StreamRPCPacketizer testStreamRpcPacketizer = new StreamRPCPacketizer(null, testListener, testInputStream, testRequest, testSessionType, testSessionId, testWV, testWV, testSdlSession);
			assertNotNull(TestValues.NOT_NULL, testStreamRpcPacketizer);
			
			// NOTE: Cannot test thread handling methods currently.
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}		
	}
}