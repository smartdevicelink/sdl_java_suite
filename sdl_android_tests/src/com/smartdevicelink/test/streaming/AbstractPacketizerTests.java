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

public class AbstractPacketizerTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testConstructors () {
		
		byte testSessionId           = (byte) 0x0A;
		byte testWV                  = (byte) 0x0B;
		SessionType testSessionType  = SessionType.RPC;
		RPCRequest  testRequest      = new RPCRequest(MSG);
		InputStream testInputStream  = null; 
		IStreamListener testListener = new MockStreamListener();
		MockPacketizer testAP    = null;
		
		// Test -- AbstractPacketizer (IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID) throws IOException
		try {
			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			testAP = new MockPacketizer(testListener, testInputStream, testSessionType, testSessionId);
			assertNotNull(MSG, testAP);
			assertEquals(MSG, testListener, testAP.getListener());
			assertEquals(MSG, testInputStream, testAP.getInputStream());
			assertEquals(MSG, testSessionType, testAP.getSessionType());
			assertEquals(MSG, testSessionId, testAP.getSessionId());
			
			testAP = new MockPacketizer(null, null, null, testSessionId);
			assertNotNull(MSG, testAP);
			assertNull(MSG, testAP.getListener());
			assertNull(MSG, testAP.getInputStream());
			assertNull(MSG, testAP.getSessionType());
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		} catch (Exception e) {
			fail("Exception was thrown.");
		}
		
		// Test -- AbstractPacketizer (IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) throws IOException
		try {

			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			testAP = new MockPacketizer(testListener, testInputStream, testRequest, testSessionType, testSessionId, testWV);
			assertNotNull(MSG, testAP);
			assertEquals(MSG, testListener, testAP.getListener());
			assertEquals(MSG, testInputStream, testAP.getInputStream());
			assertEquals(MSG, testSessionType, testAP.getSessionType());
			assertEquals(MSG, testSessionId, testAP.getSessionId());
			assertEquals(MSG, testWV, testAP.getWiproVersion());
			assertEquals(MSG, testRequest.getFunctionName(), testAP.getRPCRequest().getFunctionName());
			
			testAP = new MockPacketizer(null, null, null, null, testSessionId, testWV);
			assertNotNull(MSG, testAP);
			assertNull(MSG, testAP.getListener());
			assertNull(MSG, testAP.getInputStream());
			assertNull(MSG, testAP.getSessionType());
			assertNull(MSG, testAP.getRPCRequest());
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		} catch (Exception e) {
			fail("Exception was thrown.");
		}		
	}

}

class MockStreamListener implements IStreamListener {
	public MockStreamListener () { }
	@Override public void sendStreamPacket(ProtocolMessage pm) { }
}

class MockPacketizer extends AbstractPacketizer {
	public MockPacketizer (IStreamListener l, InputStream i, SessionType s, byte sid) throws IOException {
		super (l, i, s, sid);
	}
	
	public MockPacketizer (IStreamListener l, InputStream i, RPCRequest r, SessionType s, byte sid, byte w) throws IOException {
		super (l, i, r, s, sid, w);
	}

	@Override public void start() throws IOException { }
	@Override public void stop() { }
	
	public IStreamListener getListener () { return _streamListener; }
	public InputStream getInputStream  () { return is;              }
	public SessionType getSessionType  () { return _session;        }
	public byte getSessionId           () { return _rpcSessionID;   }
	public RPCRequest getRPCRequest    () { return _request;        }
	public byte getWiproVersion        () { return _wiproVersion;   }
}