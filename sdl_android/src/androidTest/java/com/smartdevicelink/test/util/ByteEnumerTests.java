package com.smartdevicelink.test.util;

import java.util.Vector;

import junit.framework.TestCase;

import com.smartdevicelink.test.Test;
import com.smartdevicelink.util.ByteEnumer;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.util.ByteEnumer}
 */
public class ByteEnumerTests extends TestCase {
	
	/**
	 * This is a unit test for the following methods :
	 * {@link com.smartdevicelink.util.ByteEnumer#value()}
	 * {@link com.smartdevicelink.util.ByteEnumer#getValue()}
	 * {@link com.smartdevicelink.util.ByteEnumer#getName()}
	 * {@link com.smartdevicelink.util.ByteEnumer#equals(ByteEnumer)}
	 * {@link com.smartdevicelink.util.ByteEnumer#eq(ByteEnumer)}
	 */
	public void testStoredValues () {
		
		// Test Values
		byte           testByte   = (byte) 0x01;
		String         testString = "test";
		MockByteEnumer testObject = new MockByteEnumer(testByte, null);
		
		// Comparison Values
		MockByteEnumer expectedObject   = new MockByteEnumer(testByte, testString);
		MockByteEnumer actualObject     = new MockByteEnumer(testByte, testString);		
		byte           actualByte1      = expectedObject.getValue();
		byte           actualByte2      = expectedObject.value();
		String         actualString     = expectedObject.getName();
		String         actualNullString = testObject.getName();
		
		// Valid Tests
		assertNotNull(Test.NOT_NULL, expectedObject);
		assertEquals(Test.MATCH, testByte, actualByte1);
		assertEquals(Test.MATCH, testByte, actualByte2);
		assertEquals(Test.MATCH, testString, actualString);
		assertTrue(Test.TRUE, actualObject.equals(expectedObject));
		assertTrue(Test.TRUE, actualObject.eq(expectedObject));
		
		// Invalid/Null Tests
		assertNull(Test.NULL, actualNullString);
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.util.ByteEnumer#get(Vector, byte)}
	 * {@link com.smartdevicelink.util.ByteEnumer#get(Vector, String)}
	 */
	public void testSearchMethods () {
		
		// Test Values
		byte               testByte1       = (byte) 0x01;
		byte               testByte2       = (byte) 0x02;
		String             testString1     = "test_1";
		String             testString2     = "test_2";
		Vector<ByteEnumer> testList        = new Vector<ByteEnumer>();
		Vector<String>     testInvalidList = new Vector<String>();
		
		// Comparison Values
		MockByteEnumer expectedObject1 = new MockByteEnumer(testByte1, testString1);
		MockByteEnumer expectedObject2 = new MockByteEnumer(testByte2, testString2);
		
		testList.add(expectedObject1);
		testList.add(expectedObject2);		
		testInvalidList.add(testString1);
		testInvalidList.add(testString2);
		
		// Valid Tests
		assertEquals(Test.MATCH, expectedObject1, ByteEnumer.get(testList, testByte1));
		assertEquals(Test.MATCH, expectedObject1, ByteEnumer.get(testList, testString1));
		assertEquals(Test.MATCH, expectedObject2, ByteEnumer.get(testList, testByte2));
		assertEquals(Test.MATCH, expectedObject2, ByteEnumer.get(testList, testString2));
		
		// Invalid/Null Tests
		assertNull(Test.NULL, ByteEnumer.get(testList, null));
		assertNull(Test.NULL, ByteEnumer.get(testInvalidList, testByte1));
		assertNull(Test.NULL, ByteEnumer.get(testInvalidList, testString1));
	}
}

/**
 * This is a mock class for testing the following : 
 * {@link com.smartdevicelink.util.ByteEnumer}
 */
class MockByteEnumer extends ByteEnumer {
	protected MockByteEnumer(byte value, String name) {
		super(value, name);
	}
}