package com.smartdevicelink.test.streaming;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.streaming.IStreamListener;
import com.smartdevicelink.streaming.StreamPacketizer;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.streaming.StreamPacketizer}
 */
public class StreamPacketizerTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.streaming.StreamPacketizer#StreamPacketizer(IStreamListener, InputStream, SessionType, byte)}
	 */
	public void testConstructor () {
		
		// Test Values
		byte            testSessionId   = (byte) 0x0A;
		SessionType     testSessionType = SessionType.RPC;
		InputStream     testInputStream = null;
		IStreamListener testListener    = new MockStreamListener();
		
		try {			
			URL url = new URL("ftp://mirror.csclub.uwaterloo.ca/index.html");
		    URLConnection urlConnection = url.openConnection();
			testInputStream = new BufferedInputStream(urlConnection.getInputStream());
			
			StreamPacketizer testStreamPacketizer = new StreamPacketizer(testListener, testInputStream, testSessionType, testSessionId);
			assertNotNull(Test.NOT_NULL, testStreamPacketizer);
						
			// NOTE: Cannot test thread handling methods currently.
			
		} catch (IOException e) {
			fail("IOException was thrown.");
		}
	}
}