package com.smartdevicelink.test.protocol.enums;

import java.util.Vector;

import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

public class FrameTypeTests extends TestCase {
	
	private Vector<FrameType> list = FrameType.getList();

	// Verifies the values are not null upon valid assignment.
	// These are not actual enums for packeting reasons so testing is different.
	public void testValidEnums () {	
		
		final byte   CONTROL_BYTE   = (byte) 0x00;
		final String CONTROL_STRING = "Control";
		
		final byte   SINGLE_BYTE   = (byte) 0x01;
		final String SINGLE_STRING = "Single";
		
		final byte   FIRST_BYTE   = (byte) 0x02;
		final String FIRST_STRING = "First";
		
		final byte   CONSECUTIVE_BYTE   = (byte) 0x03;
		final String CONSECUTIVE_STRING = "Consecutive";
		
		try {
			
			assertNotNull("FrameType list returned null", list);
			
			// Check the byte values
			FrameType enumControl = (FrameType) FrameType.get(list, CONTROL_BYTE);
			FrameType enumSingle = (FrameType) FrameType.get(list, SINGLE_BYTE);
			FrameType enumFirst = (FrameType) FrameType.get(list, FIRST_BYTE);
			FrameType enumConsecutive = (FrameType) FrameType.get(list, CONSECUTIVE_BYTE);
			
			assertNotNull("Control byte match returned null", enumControl);
			assertNotNull("Single byte match returned null", enumSingle);
			assertNotNull("First byte match returned null", enumFirst);
			assertNotNull("Consecutive byte match returned null", enumConsecutive);
			
			// Check the string values
			enumControl = (FrameType) FrameType.get(list, CONTROL_STRING);
			enumSingle = (FrameType) FrameType.get(list, SINGLE_STRING);
			enumFirst = (FrameType) FrameType.get(list, FIRST_STRING);
			enumConsecutive = (FrameType) FrameType.get(list, CONSECUTIVE_STRING);
			
			assertNotNull("Control string match returned null", enumControl);
			assertNotNull("Single string match returned null", enumSingle);
			assertNotNull("First string match returned null", enumFirst);
			assertNotNull("Consecutive string match returned null", enumConsecutive);
			
		} catch (NullPointerException exception) {
            fail("Null enum list throws NullPointerException.");
		}	
	}
	
	// Verifies that an invalid assignment is null.
	public void testInvalidEnum () {
		
		final byte   INVALID_BYTE   = (byte) 0xAB;
		final String INVALID_STRING = "Invalid";
		
		try {
			
			// Check the byte value
			FrameType enumInvalid = (FrameType) FrameType.get(list, INVALID_BYTE);
			assertNull("Invalid byte match didn't return null", enumInvalid);
			
			// Check the string value
			enumInvalid = (FrameType) FrameType.get(list, INVALID_STRING);
			assertNull("Invalid string match didn't return null", enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
					
			// Check null string lookup
			FrameType enumNull = (FrameType) FrameType.get(list, null);
			assertNull("Null lookup returns a value", enumNull);
			
		} catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}		
	}
	
	// Verifies the possible enum values of FrameType.
	public void testListEnum () {
		
		// Test Vector
		Vector<FrameType> enumTestList = new Vector<FrameType>();
		enumTestList.add(FrameType.Control);
		enumTestList.add(FrameType.Single);
		enumTestList.add(FrameType.First);
		enumTestList.add(FrameType.Consecutive);
		
		assertTrue("List does not match enum test list.",
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		FrameType[] enumValueArray = FrameType.values();
		FrameType[] enumTestArray = { FrameType.Control, FrameType.Single, 
									  FrameType.First,   FrameType.Consecutive };
		
		assertTrue("Array does not match enum values array.",
					Validator.validateFrameTypeArray(enumValueArray, enumTestArray));
	}	
}