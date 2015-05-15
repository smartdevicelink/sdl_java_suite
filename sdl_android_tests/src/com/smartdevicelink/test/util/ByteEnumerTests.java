package com.smartdevicelink.test.util;

import java.util.Vector;

import junit.framework.TestCase;

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
		assertNotNull("Value should not be null.", expectedObject);
		assertEquals("Values should match.", testByte, actualByte1);
		assertEquals("Values should match.", testByte, actualByte2);
		assertEquals("Values should match.", testString, actualString);
		assertTrue("Value should be true.", actualObject.equals(expectedObject));
		assertTrue("Value should be true.", actualObject.eq(expectedObject));
		
		// Invalid/Null Tests
		assertNull("Value should be null.", actualNullString);
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
		assertEquals("Values should match.", expectedObject1, ByteEnumer.get(testList, testByte1));
		assertEquals("Values should match.", expectedObject1, ByteEnumer.get(testList, testString1));
		assertEquals("Values should match.", expectedObject2, ByteEnumer.get(testList, testByte2));
		assertEquals("Values should match.", expectedObject2, ByteEnumer.get(testList, testString2));
		
		// Invalid/Null Tests
		assertNull("Value should be null.", ByteEnumer.get(testList, null));
		assertNull("Value should be null.", ByteEnumer.get(testInvalidList, testByte1));
		assertNull("Value should be null.", ByteEnumer.get(testInvalidList, testString1));
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