package com.smartdevicelink.test.streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

import com.smartdevicelink.protocol.ProtocolMessage;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.AbstractPacketizer;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.test.Test;

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
			
			testPacketizer1 = new MockPacketizer(testListener, testInputStream, testSessionType, testSessionId);
			testPacketizer2 = new MockPacketizer(null, null, null, testSessionId);
			testPacketizer3 = new MockPacketizer(testListener, testInputStream, testRpcRequest, testSessionType, testSessionId, testWiproVersion);
			testPacketizer4 = new MockPacketizer(null, null, null, null, testSessionId, testWiproVersion);
			
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
						
			// Invalid/Null Tests
			assertNull(Test.NULL, testPacketizer2.getListener());
			assertNull(Test.NULL, testPacketizer2.getInputStream());
			assertNull(Test.NULL, testPacketizer2.getSessionType());
			assertNull(Test.NULL, testPacketizer4.getListener());
			assertNull(Test.NULL, testPacketizer4.getInputStream());
			assertNull(Test.NULL, testPacketizer4.getSessionType());
			assertNull(Test.NULL, testPacketizer4.getRPCRequest());
			
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
class MockPacketizer extends AbstractPacketizer {
	public MockPacketizer (IStreamListener l, InputStream i, SessionType s, byte sid) throws IOException { super (l, i, s, sid); }
	public MockPacketizer (IStreamListener l, InputStream i, RPCRequest r, SessionType s, byte sid, byte w) throws IOException { super (l, i, r, s, sid, w); }

	@Override public void start() throws IOException { }
	@Override public void stop() { }
	
	public IStreamListener getListener () { return _streamListener; }
	public InputStream getInputStream  () { return is;              }
	public SessionType getSessionType  () { return _session;        }
	public byte getSessionId           () { return _rpcSessionID;   }
	public RPCRequest getRPCRequest    () { return _request;        }
	public byte getWiproVersion        () { return _wiproVersion;   }
}