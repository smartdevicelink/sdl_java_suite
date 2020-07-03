package com.smartdevicelink.test.util;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.util.BitConverter;

import junit.framework.TestCase;

import java.util.Arrays;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.util.BitConverter}
 */
public class BitConverterTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.util.BitConverter#bytesToHex(byte[])}
	 * {@link com.smartdevicelink.util.BitConverter#bytesToHex(byte[], int, int)}
	 * {@link com.smartdevicelink.util.BitConverter#hexToBytes(String)}
	 */
	public void testHexToByteConversions () {
		
		// Test Values
		byte[] testBytes = { (byte) 0x01, (byte) 0x02 };
		
		// Comparison Values
		String expectedHex = "0102";
		String actualHex1  = BitConverter.bytesToHex(testBytes);
		String actualHex2  = BitConverter.bytesToHex(testBytes, 0, testBytes.length);
		byte[] actualBytes = BitConverter.hexToBytes(expectedHex);
		
		// Valid Tests
		assertEquals("Values should match.", expectedHex, actualHex1);
		assertEquals("Values should match.", expectedHex, actualHex2);
		assertTrue("Arrays should match.", Arrays.equals(testBytes, actualBytes));
		
		// Invalid/Null Tests
		assertNull(TestValues.NULL, BitConverter.bytesToHex(null));
		assertNull(TestValues.NULL, BitConverter.bytesToHex(null, 0, 0));
		assertNull(TestValues.NULL, BitConverter.hexToBytes(null));
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.util.BitConverter#intToByteArray(int)}
	 * {@link com.smartdevicelink.util.BitConverter#intFromByteArray(byte[], int)}
	 */
	public void testIntToByteConversions () {
		
		// Comparison Values
		Integer expectedInt     = 1234;
		byte[]  expectedBytes   = { (byte)(1234 >>> 24), (byte)(1234 >>> 16),
				                    (byte)(1234 >>> 8),  (byte)(1234) };
		int     actualInt       = BitConverter.intFromByteArray(expectedBytes, 0);
		byte[]  actualBytes     = BitConverter.intToByteArray(expectedInt);
		int     actualNullBytes = BitConverter.intFromByteArray(null, 0);
		
		// Valid Tests
		assertEquals(TestValues.MATCH, expectedInt.intValue(), actualInt);
		assertTrue(TestValues.ARRAY, Arrays.equals(expectedBytes, actualBytes));
		
		// Invalid/Null Tests
		assertEquals(TestValues.MATCH, (int) -1, actualNullBytes);
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.util.BitConverter#shortToByteArray(short)}
	 * {@link com.smartdevicelink.util.BitConverter#shortFromByteArray(byte[], int)}
	 */
	public void testShortToByteConverstions () {
		
		// Comparison Values
		short  expectedShort   = 1;
		byte[] expectedBytes   = { ((byte) ((1 >> 8) & 0xff)), ((byte) (1 & 0xff)) };
		short  actualShort     = BitConverter.shortFromByteArray(expectedBytes, 0);
		byte[] actualBytes     = BitConverter.shortToByteArray(expectedShort);
		short  actualNullBytes = BitConverter.shortFromByteArray(null, 0);
		
		// Valid Tests
		assertEquals(TestValues.MATCH, expectedShort, actualShort);
		assertTrue(TestValues.ARRAY, Arrays.equals(expectedBytes, actualBytes));
		
		// Invalid/Null Tests
		assertEquals(TestValues.MATCH, (short) -1, actualNullBytes);
	}		
}