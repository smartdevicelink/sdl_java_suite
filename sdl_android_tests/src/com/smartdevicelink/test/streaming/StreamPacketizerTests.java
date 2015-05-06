package com.smartdevicelink.test.streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;

import junit.framework.TestCase;

public class StreamPacketizerTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	
	public void testMethods () {
		
		byte testSessionId           = (byte) 0x0A;
		SessionType testSessionType  = SessionType.RPC;
		InputStream testInputStream  = null;
		IStreamListener testListener = new MockStreamListener();
		
		// Test -- StreamPacketizer (IStreamListener streamListener, InputStream is, SessionType sType, byte rpcSessionID) throws IOException
		try {
			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			StreamPacketizer testSP = new StreamPacketizer(testListener, testInputStream, testSessionType, testSessionId);
			assertNotNull(MSG, testSP);
			
			// TODO: Thread testing
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}
	}	
}