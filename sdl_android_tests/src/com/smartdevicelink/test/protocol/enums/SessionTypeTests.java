package com.smartdevicelink.test.protocol.enums;

import java.util.Vector;

import junit.framework.TestCase;

import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.test.Validator;

public class SessionTypeTests extends TestCase {
	
	private Vector<SessionType> list = SessionType.getList();
	
	// Verifies the values are not null upon valid assignment.
	// These are not actual enums for packeting reasons so testing is different.
	public void testValidEnums () {
		
		final byte   HEARTBEAT_BYTE   = (byte) 0x00;
		final String HEARTBEAT_STRING = "CONTROL";
		
		final byte   RPC_BYTE   = (byte) 0x07;
		final String RPC_STRING = "RPC";
		
		final byte   PCM_BYTE   = (byte) 0x0A;
		final String PCM_STRING = "PCM";
		
		final byte   NAV_BYTE   = (byte) 0x0B;
		final String NAV_STRING = "NAV";
		
		final byte   BULK_DATA_BYTE   = (byte) 0x0F;
		final String BULK_DATA_STRING = "BULK_DATA";
		
		try {
			
			assertNotNull("SessionType list returned null", list);
			
			// Check the byte values
			SessionType enumHeartbeat = (SessionType) SessionType.get(list, HEARTBEAT_BYTE);
			SessionType enumRPC       = (SessionType) SessionType.get(list, RPC_BYTE);
			SessionType enumPCM       = (SessionType) SessionType.get(list, PCM_BYTE);
			SessionType enumNAV       = (SessionType) SessionType.get(list, NAV_BYTE);
			SessionType enumBulkData  = (SessionType) SessionType.get(list, BULK_DATA_BYTE);
			
			assertNotNull("Start session byte match returned null", enumHeartbeat);
			assertNotNull("Single byte match returned null",        enumRPC);
			assertNotNull("First byte match returned null",         enumPCM);
			assertNotNull("Consecutive byte match returned null",   enumNAV);
			assertNotNull("Consecutive byte match returned null",   enumBulkData);
			
			// Check the string values
			enumHeartbeat = (SessionType) SessionType.get(list, HEARTBEAT_STRING);
			enumRPC       = (SessionType) SessionType.get(list, RPC_STRING);
			enumPCM       = (SessionType) SessionType.get(list, PCM_STRING);
			enumNAV       = (SessionType) SessionType.get(list, NAV_STRING);
			enumBulkData  = (SessionType) SessionType.get(list, BULK_DATA_STRING);
			
			assertNotNull("Start session string match returned null", enumHeartbeat);
			assertNotNull("Single string match returned null",        enumRPC);
			assertNotNull("First string match returned null",         enumPCM);
			assertNotNull("Consecutive string match returned null",   enumNAV);
			assertNotNull("Consecutive string match returned null",   enumBulkData);
			
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
			SessionType enumInvalid = (SessionType) SessionType.get(list, INVALID_BYTE);
			assertNull("Invalid byte match didn't return null", enumInvalid);
			
			// Check the string value
			enumInvalid = (SessionType) SessionType.get(list, INVALID_STRING);
			assertNull("Invalid string match didn't return null", enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
			
			// Check null string lookup
			SessionType enumNull = (SessionType) SessionType.get(list, null);
			assertNull("Null lookup returns a value", enumNull);
			
		} catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	// Verifies the possible enum values of SessionType.
	public void testListEnum () {
		// Test Vector
		Vector<SessionType> enumTestList = new Vector<SessionType>();			
		enumTestList.add(SessionType.RPC);
		enumTestList.add(SessionType.PCM);
		enumTestList.add(SessionType.NAV);
		enumTestList.add(SessionType.BULK_DATA);
		enumTestList.add(SessionType.CONTROL);
		
		assertTrue("List does not match enum test list.",
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		SessionType[] enumValueArray = SessionType.values();
		SessionType[] enumTestArray = { SessionType.RPC, SessionType.PCM, 
									    SessionType.NAV, SessionType.BULK_DATA,
									    SessionType.CONTROL };
		
		assertTrue("Array does not match enum values array.",
					Validator.validateSessionTypeArray(enumValueArray, enumTestArray));
	}
}