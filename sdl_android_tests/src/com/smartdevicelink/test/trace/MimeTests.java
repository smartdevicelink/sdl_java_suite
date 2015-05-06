package com.smartdevicelink.test.trace;

import java.io.UnsupportedEncodingException;

import com.smartdevicelink.trace.Mime;

import junit.framework.TestCase;

public class MimeTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	String test = "test";
	String testEncoded = "dGVzdA==";
	
	public void testEncoding () {
		
		try {			
			byte[] testBytes = test.getBytes("US-ASCII");
				
			// Test -- base64Encode (String string)
			assertEquals(MSG, testEncoded, Mime.base64Encode(test));
		
			// Test -- base64Encode(byte bytesToEncode[])
			assertEquals(MSG, testEncoded, Mime.base64Encode(testBytes));
			
			// Test -- base64Encode(byte bytesToEncode[], int offset, int length)
			assertEquals(MSG, testEncoded, Mime.base64Encode(testBytes, 0, testBytes.length));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	// Cannot test base64Decode method because it is not public
}