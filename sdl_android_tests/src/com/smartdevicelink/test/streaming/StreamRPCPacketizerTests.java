package com.smartdevicelink.test.streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamRPCPacketizer;

import junit.framework.TestCase;

public class StreamRPCPacketizerTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testMethods () {
		
		byte testSessionId           = (byte) 0x0A;
		byte testWV                  = (byte) 0x0B;
		RPCRequest  testRequest      = new RPCRequest(MSG);
		SessionType testSessionType  = SessionType.RPC;
		InputStream testInputStream  = null;
		IStreamListener testListener = new MockStreamListener();
		
		// Test -- StreamRPCPacketizer(IStreamListener streamListener, InputStream is, RPCRequest request, SessionType sType, byte rpcSessionID, byte wiproVersion) throws IOException
		try {
			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			StreamRPCPacketizer testSP = new StreamRPCPacketizer(testListener, testInputStream, testRequest, testSessionType, testSessionId, testWV);
			assertNotNull(MSG, testSP);
			
			// TODO: Thread testing
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		} catch (Exception e) {
			fail("Exception was thrown.");
		}		
	}
}