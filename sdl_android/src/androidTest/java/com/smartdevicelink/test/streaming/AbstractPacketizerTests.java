package com.smartdevicelink.test.streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

import com.smartdevicelink.SdlConnection.ISdlConnectionListener;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.transport.BTTransportConfig;
import com.smartdevicelink.transport.BaseTransportConfig;

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
		MockPacketizer  testPacketizer4  = null;
		IStreamListener testListener     = new MockStreamListener();
		
		try {
			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			MockInterfaceBroker _interfaceBroker = new MockInterfaceBroker();
			BaseTransportConfig _transportConfig = new BTTransportConfig(true);
		
			testSdlSession = SdlSession.createSession(testWiproVersion,_interfaceBroker, _transportConfig);
			
			testPacketizer1 = new MockPacketizer(testListener, testInputStream, testSessionType, testSessionId, testSdlSession);
			testPacketizer2 = new MockPacketizer(null, null, null, testSessionId, testSdlSession);
			testPacketizer3 = new MockPacketizer(testListener, testInputStream, testRpcRequest, testSessionType, testSessionId, testWiproVersion, testSdlSession);
			testPacketizer4 = new MockPacketizer(null, null, null, null, testSessionId, testWiproVersion, null);
			
			// Valid Tests
			assertNotNull(Test.NOT_NULL, testPacketizer1);
			assertNotNull(Test.NOT_NULL, testPacketizer2);
			assertNotNull(Test.NOT_NULL, testPacketizer3);
			assertNotNull(Test.NOT_NULL, testPacketizer4);
			
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
			assertNull(Test.NULL, testPacketizer4.getListener());
			assertNull(Test.NULL, testPacketizer4.getInputStream());
			assertNull(Test.NULL, testPacketizer4.getSessionType());
			assertNull(Test.NULL, testPacketizer4.getRPCRequest());
			assertNull(Test.NULL, testPacketizer4.getSdlSession());
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}
	}
}

/**
 * This is a mock class for testing the following : 
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
class MockStreamListener implements IStreamListener {
	public MockStreamListener () { }
	@Override public void sendStreamPacket(ProtocolMessage pm) { }
}

/**
 * This is a mock class for testing the following : 
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
class MockInterfaceBroker implements ISdlConnectionListener {
	public MockInterfaceBroker () { }
	@Override
	public void onTransportDisconnected(String info) {
		
	}
	@Override
	public void onTransportError(String info, Exception e) {
		
	}
	@Override
	public void onProtocolMessageReceived(ProtocolMessage msg) {
		
	}
	@Override
	public void onProtocolSessionStartedNACKed(SessionType sessionType,
			byte sessionID, byte version, String correlationID) {
		
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

/**
 * This is a mock class for testing the following : 
 * {@link com.smartdevicelink.streaming.AbstractPacketizer}
 */
class MockPacketizer extends AbstractPacketizer {
	public MockPacketizer (IStreamListener l, InputStream i, SessionType s, byte sid, SdlSession sdlsession) throws IOException { super (l, i, s, sid, sdlsession); }
	public MockPacketizer (IStreamListener l, InputStream i, RPCRequest r, SessionType s, byte sid, byte w, SdlSession sdlsession) throws IOException { super (l, i, r, s, sid, w, sdlsession); }

	@Override public void start() throws IOException { }
	@Override public void stop() { }
	
	public IStreamListener getListener () { return _streamListener; }
	public InputStream getInputStream  () { return is;              }
	public SessionType getSessionType  () { return _serviceType;    }
	public SdlSession getSdlSession    () { return _session;    	}
	public byte getSessionId           () { return _rpcSessionID;   }
	public RPCRequest getRPCRequest    () { return _request;        }
	public byte getWiproVersion        () { return _wiproVersion;   }
	
	@Override public void pause() { }
	@Override public void resume() { }
}