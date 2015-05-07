package com.smartdevicelink.test.utils;

import java.util.Arrays;

import com.smartdevicelink.util.BitConverter;

import junit.framework.TestCase;

public class BitConverterTests extends TestCase {

	public void testConverterMethods () {
		
		// Test bytesToHex & hextToBytes
		
		byte[] test = { (byte) 0x01, (byte) 0x02 };
		String testHex = "0102";
		
		assertEquals("Values should match.", testHex, BitConverter.bytesToHex(test));
		assertEquals("Values should match.", testHex, BitConverter.bytesToHex(test, 0, test.length));		
		assertTrue("Values should match.", Arrays.equals(test, BitConverter.hexToBytes(testHex)));
		
		// Test intToByteArray intFromByteArray
		Integer testInt = 1234;
		byte[] intbyte = { (byte)(1234 >>> 24), (byte)(1234 >>> 16), (byte)(1234 >>> 8), (byte)(1234) };
		assertTrue("Values should match.", Arrays.equals(intbyte, BitConverter.intToByteArray(testInt)));
		assertEquals("Values should match.", (int) testInt, BitConverter.intFromByteArray(intbyte, 0));
		
		// Test shortToByteArray shortFromByteArray
		short shorty = 1;
		byte[] shortbyte = { ((byte) ((1 >> 8) & 0xff)), ((byte) (1 & 0xff)) };
		assertTrue("Values should match.", Arrays.equals(shortbyte, BitConverter.shortToByteArray(shorty)));
		assertEquals("Values should match.", shorty, BitConverter.shortFromByteArray(shortbyte, 0));
		
	}		
}