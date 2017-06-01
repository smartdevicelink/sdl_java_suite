package com.smartdevicelink.test.protocol.enums;

import android.test.AndroidTestCase;

import java.util.Vector;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.FrameType;
import com.smartdevicelink.test.Validator;

public class FrameTypeTests extends AndroidTestCase {
	
	private Vector<FrameType> list = FrameType.getList();

	// Verifies the values are not null upon valid assignment.
	// These are not actual enums for packeting reasons so testing is different.
	public void testValidEnums () {	
		
		final byte   CONTROL_BYTE   = (byte) 0x00;
		final String CONTROL_STRING = mContext.getString(R.string.control);
		
		final byte   SINGLE_BYTE   = (byte) 0x01;
		final String SINGLE_STRING = mContext.getString(R.string.single);
		
		final byte   FIRST_BYTE   = (byte) 0x02;
		final String FIRST_STRING = mContext.getString(R.string.first);
		
		final byte   CONSECUTIVE_BYTE   = (byte) 0x03;
		final String CONSECUTIVE_STRING = mContext.getString(R.string.consecutive);
		
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
            fail(mContext.getString(R.string.null_enum_list_throws_null_pointer_exception));
		}	
	}
	
	// Verifies that an invalid assignment is null.
	public void testInvalidEnum () {
		
		final byte   INVALID_BYTE   = (byte) 0xAB;
		final String INVALID_STRING = mContext.getString(R.string.invalid_enum);
		
		try {
			
			// Check the byte value
			FrameType enumInvalid = (FrameType) FrameType.get(list, INVALID_BYTE);
			assertNull(mContext.getString(R.string.invalid_byte_match_didnt_return_null), enumInvalid);
			
			// Check the string value
			enumInvalid = (FrameType) FrameType.get(list, INVALID_STRING);
			assertNull(mContext.getString(R.string.invalid_string_match_didnt_return_null), enumInvalid);
			
		} catch (IllegalArgumentException exception) {
			fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	// Verifies that a null assignment is invalid.
	public void testNullEnum () {		
		try {
					
			// Check null string lookup
			FrameType enumNull = (FrameType) FrameType.get(list, null);
			assertNull(mContext.getString(R.string.null_lookup_returns_a_value), enumNull);
			
		} catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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
		
		assertTrue(mContext.getString(R.string.list_does_not_match_enum_test_list),
					list.containsAll(enumTestList) &&
					enumTestList.containsAll(list));
		
		// Test Array
		FrameType[] enumValueArray = FrameType.values();
		FrameType[] enumTestArray = { FrameType.Control, FrameType.Single, 
									  FrameType.First,   FrameType.Consecutive };
		
		assertTrue(mContext.getString(R.string.array_does_not_match_enum_values_array),
					Validator.validateFrameTypeArray(enumValueArray, enumTestArray));
	}	
}