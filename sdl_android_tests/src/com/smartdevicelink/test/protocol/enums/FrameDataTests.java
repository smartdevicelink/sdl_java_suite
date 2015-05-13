package com.smartdevicelink.test.protocol.enums;

import java.util.Vector;

import junit.framework.TestCase;

import com.smartdevicelink.protocol.enums.FrameData;
import com.smartdevicelink.test.Validator;

public class FrameDataTests extends TestCase {
	
	private Vector<FrameData> list = FrameData.getList();
	
	// Verifies the values are not null upon valid assignment.
	// These are not actual enums for packeting reasons so testing is different.
	public void testValidEnums () {
		
		final byte   START_SESSION_BYTE   = (byte) 0x01;
		final String START_SESSION_STRING = "StartSession";
		
		final byte   START_SESSION_ACK_BYTE   = (byte) 0x02;
		final String START_SESSION_ACK_STRING = "StartSessionACK";
		
		final byte   START_SESSION_NACK_BYTE   = (byte) 0x03;
		final String START_SESSION_NACK_STRING = "StartSessionNACK";
		
		final byte   END_SESSION_BYTE   = (byte) 0x04;
		final String END_SESSION_STRING = "EndSession";
		
		try {
			
			assertNotNull("FrameData list returned null", list);
			
			// Check the byte values
			FrameData enumSS     = (FrameData) FrameData.get(list, START_SESSION_BYTE);
			FrameData enumSSACK  = (FrameData) FrameData.get(list, START_SESSION_ACK_BYTE);
			FrameData enumSSNACK = (FrameData) FrameData.get(list, START_SESSION_NACK_BYTE);
			FrameData enumES     = (FrameData) FrameData.get(list, END_SESSION_BYTE);
			
			assertNotNull("Start session byte match returned null", enumSS);
			assertNotNull("Single byte match returned null",        enumSSACK);
			assertNotNull("First byte match returned null",         enumSSNACK);
			assertNotNull("Consecutive byte match returned null",   enumES);
			
			// Check the string values
			enumSS     = (FrameData) FrameData.get(list, START_SESSION_STRING);
			enumSSACK  = (FrameData) FrameData.get(list, START_SESSION_ACK_STRING);
			enumSSNACK = (FrameData) FrameData.get(list, START_SESSION_NACK_STRING);
			enumES     = (FrameData) FrameData.get(list, END_SESSION_STRING);
			
			assertNotNull("Start session string match returned null", enumSS);
			assertNotNull("Single string match returned null",        enumSSACK);
			assertNotNull("First string match returned null",         enumSSNACK);
			assertNotNull("Consecutive string match returned null",   enumES);
			
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
			FrameData enumInvalid = (FrameData) FrameData.get(list, INVALID_BYTE);
			assertNull("Invalid byte match didn't return null", enumInvalid);
			
			// Check the string value
			enumInvalid = (FrameData) FrameData.get(list, INVALID_STRING);
			assertNull("Invalid string match didn't return null", enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
			
			// Check null string lookup
			FrameData enumNull = (FrameData) FrameData.get(list, null);
			assertNull("Null lookup returns a value", enumNull);
			
		} catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	// Verifies the possible enum values of FrameType.
	public void testListEnum () {
		// Test Vector
		Vector<FrameData> enumTestList = new Vector<FrameData>();
		enumTestList.add(FrameData.StartSession);
		enumTestList.add(FrameData.StartSessionACK);
		enumTestList.add(FrameData.StartSessionNACK);
		enumTestList.add(FrameData.EndSession);
		
		assertTrue("List does not match enum test list.",
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		FrameData[] enumValueArray = FrameData.values();
		FrameData[] enumTestArray = { FrameData.StartSession,     FrameData.StartSessionACK, 
									  FrameData.StartSessionNACK, FrameData.EndSession };
		
		assertTrue("Array does not match enum values array.",
					Validator.validateFrameDataArray(enumValueArray, enumTestArray));
	}
}