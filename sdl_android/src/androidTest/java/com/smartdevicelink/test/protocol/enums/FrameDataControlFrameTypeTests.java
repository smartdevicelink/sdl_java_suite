package com.smartdevicelink.test.protocol.enums;

import android.test.AndroidTestCase;

import java.util.Vector;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.FrameDataControlFrameType;
import com.smartdevicelink.test.Validator;


public class FrameDataControlFrameTypeTests extends AndroidTestCase {
	
	private Vector<FrameDataControlFrameType> list = FrameDataControlFrameType.getList();
	
	// Verifies the values are not null upon valid assignment.
	// These are not actual enums for packeting reasons so testing is different.
	public void testValidEnums () {
		
		final byte   START_SESSION_BYTE   = (byte) 0x01;
		final String START_SESSION_STRING = mContext.getString(R.string.start_session);
		
		final byte   START_SESSION_ACK_BYTE   = (byte) 0x02;
		final String START_SESSION_ACK_STRING = mContext.getString(R.string.startsessionack);
		
		final byte   START_SESSION_NACK_BYTE   = (byte) 0x03;
		final String START_SESSION_NACK_STRING = mContext.getString(R.string.startsessionnack);
		
		final byte   END_SESSION_BYTE   = (byte) 0x04;
		final String END_SESSION_STRING = mContext.getString(R.string.endsession);
		
		final byte   END_SESSION_ACK_BYTE   = (byte) 0x05;
		final String END_SESSION_ACK_STRING = mContext.getString(R.string.endsessionack);
		
		final byte   END_SESSION_NACK_BYTE   = (byte) 0x06;
		final String END_SESSION_NACK_STRING = mContext.getString(R.string.endsessionnack);
		
		final byte   HEARTBEAT_BYTE   = (byte) 0x00;
		final String HEARTBEAT_STRING = mContext.getString(R.string.heartbeat);
		
		final byte   HEARTBEAT_ACK_BYTE   = (byte) 0xFF;
		final String HEARTBEAT_ACK_STRING = mContext.getString(R.string.heartbeatack);
		
		try {
			
			assertNotNull("FrameDataControlFrameType list returned null", list);
			
			// Check the byte values
			FrameDataControlFrameType enumSS     = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_BYTE);
			FrameDataControlFrameType enumSSACK  = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_ACK_BYTE);
			FrameDataControlFrameType enumSSNACK = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_NACK_BYTE);
			FrameDataControlFrameType enumES     = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_BYTE);
			FrameDataControlFrameType enumESACK  = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_ACK_BYTE);
			FrameDataControlFrameType enumESNACK = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_NACK_BYTE);
			FrameDataControlFrameType enumH      = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, HEARTBEAT_BYTE);
			FrameDataControlFrameType enumHACK   = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, HEARTBEAT_ACK_BYTE);
			
			assertNotNull("Start session byte match returned null",      enumSS);
			assertNotNull("Start session ack byte match returned null",  enumSSACK);
			assertNotNull("Start session nack byte match returned null", enumSSNACK);
			assertNotNull("End session byte match returned null",        enumES);
			assertNotNull("End session ack byte match returned null",    enumESACK);
			assertNotNull("End session nack byte match returned null",   enumESNACK);
			assertNotNull("Heartbeat byte match returned null",          enumH);
			assertNotNull("Heartbeat ack byte match returned null",      enumHACK);
			
			// Check the string values
			enumSS     = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_STRING);
			enumSSACK  = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_ACK_STRING);
			enumSSNACK = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, START_SESSION_NACK_STRING);
			enumES     = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_STRING);
			enumESACK  = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_ACK_STRING);
			enumESNACK = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, END_SESSION_NACK_STRING);
			enumH      = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, HEARTBEAT_STRING);
			enumHACK   = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, HEARTBEAT_ACK_STRING);
			
			assertNotNull("Start session string match returned null",      enumSS);
			assertNotNull("Start session ack string match returned null",  enumSSACK);
			assertNotNull("Start session nack string match returned null", enumSSNACK);
			assertNotNull("End session string match returned null",        enumES);
			assertNotNull("End session ack string match returned null",    enumESACK);
			assertNotNull("End session nack string match returned null",   enumESNACK);
			assertNotNull("Heartbeat string match returned null",          enumH);
			assertNotNull("Heartbeat ack string match returned null",      enumHACK);
			
		} catch (NullPointerException exception) {
            fail(mContext.getString(R.string.null_enum_list_throws_null_pointer_exception));
		}		
	}
	
	// Verifies that an invalid assignment is null.
	public void testInvalidEnum () {
		
		final byte   INVALID_BYTE   = (byte) 0xAB;
		final String INVALID_STRING = mContext.getString(R.string.invalid_enum);
		
		try {
			
			// Check the byte value
			FrameDataControlFrameType enumInvalid = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, INVALID_BYTE);
			assertNull(mContext.getString(R.string.invalid_byte_match_didnt_return_null), enumInvalid);
			
			// Check the string value
			enumInvalid = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, INVALID_STRING);
			assertNull(mContext.getString(R.string.invalid_string_match_didnt_return_null), enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
			
			// Check null string lookup
			FrameDataControlFrameType enumNull = (FrameDataControlFrameType) FrameDataControlFrameType.get(list, null);
			assertNull(mContext.getString(R.string.null_lookup_returns_a_value), enumNull);
			
		} catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	// Verifies the possible enum values of FrameType.
	public void testListEnum () {
		// Test Vector
		Vector<FrameDataControlFrameType> enumTestList = new Vector<FrameDataControlFrameType>();
		enumTestList.add(FrameDataControlFrameType.Heartbeat);
		enumTestList.add(FrameDataControlFrameType.StartSession);
		enumTestList.add(FrameDataControlFrameType.StartSessionACK);
		enumTestList.add(FrameDataControlFrameType.StartSessionNACK);
		enumTestList.add(FrameDataControlFrameType.EndSession);
		enumTestList.add(FrameDataControlFrameType.EndSessionACK);
		enumTestList.add(FrameDataControlFrameType.EndSessionNACK);
		enumTestList.add(FrameDataControlFrameType.ServiceDataACK);
		enumTestList.add(FrameDataControlFrameType.HeartbeatACK);
		
		assertTrue(mContext.getString(R.string.list_does_not_match_enum_test_list),
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		FrameDataControlFrameType[] enumValueArray = FrameDataControlFrameType.values();
		FrameDataControlFrameType[] enumTestArray = { 
				FrameDataControlFrameType.Heartbeat,       FrameDataControlFrameType.StartSession,
				FrameDataControlFrameType.StartSessionACK, FrameDataControlFrameType.StartSessionNACK,
				FrameDataControlFrameType.EndSession,      FrameDataControlFrameType.EndSessionACK,
				FrameDataControlFrameType.EndSessionNACK,  FrameDataControlFrameType.ServiceDataACK,
				FrameDataControlFrameType.HeartbeatACK, };
		
		assertTrue(mContext.getString(R.string.array_does_not_match_enum_values_array),
					Validator.validateFrameDataControlFrameTypeArray(enumValueArray, enumTestArray));
	}

}
