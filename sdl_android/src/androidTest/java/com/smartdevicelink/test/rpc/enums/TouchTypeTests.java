package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.TouchType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TouchType}
 */
public class TouchTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.begin_caps);
		TouchType enumBegin = TouchType.valueForString(example);
		example = mContext.getString(R.string.move_caps);
		TouchType enumMove = TouchType.valueForString(example);
		example = mContext.getString(R.string.end_caps);
		TouchType enumEnd = TouchType.valueForString(example);
		
		assertNotNull("BEGIN returned null", enumBegin);
		assertNotNull("MOVE returned null", enumMove);
		assertNotNull("END returned null", enumEnd);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    TouchType temp = TouchType.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    TouchType temp = TouchType.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	

	/**
	 * Verifies the possible enum values of TouchType.
	 */
	public void testListEnum() {
 		List<TouchType> enumValueList = Arrays.asList(TouchType.values());

		List<TouchType> enumTestList = new ArrayList<TouchType>();
		enumTestList.add(TouchType.BEGIN);
		enumTestList.add(TouchType.MOVE);
		enumTestList.add(TouchType.END);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}