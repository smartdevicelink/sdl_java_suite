package com.smartdevicelink.test.trace;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.trace.Mime;

import junit.framework.TestCase;

import java.io.UnsupportedEncodingException;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.Mime}
 */
public class MimeTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.trace.Mime#base64Encode(byte[])}
	 * {@link com.smartdevicelink.trace.Mime#base64Encode(String)}
	 * {@link com.smartdevicelink.trace.Mime#base64Encode(byte[], int, int)}
	 */
	public void testEncoding () {
		
		try {
			// Test Values
			String testString  = "test";
			byte[] testBytes   = testString.getBytes("US-ASCII");
			
			// Comparison Values
			String expectedEncodedString = "dGVzdA==";
			String actualNullResult1    = Mime.base64Encode((byte[]) null);
			String actualNullResult2    = Mime.base64Encode((String) null);	
			String actualNullResult3    = Mime.base64Encode(null, 0, 0); 
			String actualInvalidResult  = Mime.base64Encode(testBytes, 35, 2);
			String actualEncodedString1 = Mime.base64Encode(testString);
			String actualEncodedString2 = Mime.base64Encode(testBytes);
			String actualEncodedString3 = Mime.base64Encode(testBytes, 0, testBytes.length);
			
			// Valid Tests
			assertEquals(TestValues.MATCH, expectedEncodedString, actualEncodedString1);
			assertEquals(TestValues.MATCH, expectedEncodedString, actualEncodedString2);
			assertEquals(TestValues.MATCH, expectedEncodedString, actualEncodedString3);
			
			// Invalid/Null Tests
			assertNull(TestValues.NULL, actualNullResult1);
			assertNull(TestValues.NULL, actualNullResult2);
			assertNull(TestValues.NULL, actualNullResult3);
			assertNull(TestValues.NULL, actualInvalidResult);
						
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}