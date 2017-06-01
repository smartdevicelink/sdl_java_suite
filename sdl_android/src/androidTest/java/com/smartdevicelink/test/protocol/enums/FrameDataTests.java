package com.smartdevicelink.test.protocol.enums;

import android.test.AndroidTestCase;

import java.util.Vector;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.FrameData;
import com.smartdevicelink.test.Validator;

public class FrameDataTests extends AndroidTestCase {
	
	private Vector<FrameData> list = FrameData.getList();
	
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
            fail(mContext.getString(R.string.null_enum_list_throws_null_pointer_exception));
		}		
	}
	
	// Verifies that an invalid assignment is null.
	public void testInvalidEnum () {
		
		final byte   INVALID_BYTE   = (byte) 0xAB;
		final String INVALID_STRING = mContext.getString(R.string.invalid_enum);
		
		try {
			
			// Check the byte value
			FrameData enumInvalid = (FrameData) FrameData.get(list, INVALID_BYTE);
			assertNull(mContext.getString(R.string.invalid_byte_match_didnt_return_null), enumInvalid);
			
			// Check the string value
			enumInvalid = (FrameData) FrameData.get(list, INVALID_STRING);
			assertNull(mContext.getString(R.string.invalid_string_match_didnt_return_null), enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
			
			// Check null string lookup
			FrameData enumNull = (FrameData) FrameData.get(list, null);
			assertNull(mContext.getString(R.string.null_lookup_returns_a_value), enumNull);
			
		} catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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
		
		assertTrue(mContext.getString(R.string.list_does_not_match_enum_test_list),
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		FrameData[] enumValueArray = FrameData.values();
		FrameData[] enumTestArray = { FrameData.StartSession,     FrameData.StartSessionACK, 
									  FrameData.StartSessionNACK, FrameData.EndSession };
		
		assertTrue(mContext.getString(R.string.array_does_not_match_enum_values_array),
					Validator.validateFrameDataArray(enumValueArray, enumTestArray));
	}
}