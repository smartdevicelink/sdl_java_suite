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
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.StreamRpcPacketizer}
 */
public class StreamRpcPacketizerTests extends TestCase {
		
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.StreamRPCPacketizer#StreamRPCPacketizer(IStreamListener, InputStream, RPCRequest, SessionType, byte, byte)}
	 */
	public void testConstructor () {
		
		// Test Values
		byte testSessionId           = (byte) 0x0A;
		byte testWV                  = (byte) 0x0B;
		RPCRequest  testRequest      = new RPCRequest("test");
		SessionType testSessionType  = SessionType.RPC;
		InputStream testInputStream  = null;
		IStreamListener testListener = new MockStreamListener();
		
		try {			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			StreamRPCPacketizer testStreamRpcPacketizer = new StreamRPCPacketizer(null, testListener, testInputStream, testRequest, testSessionType, testSessionId, testWV, testWV);
			assertNotNull(Test.NOT_NULL, testStreamRpcPacketizer);
			
			// NOTE: Cannot test thread handling methods currently.
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}		
	}
}