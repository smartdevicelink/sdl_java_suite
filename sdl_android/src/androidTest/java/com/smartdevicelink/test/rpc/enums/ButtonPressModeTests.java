package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ButtonPressMode}
 */
public class ButtonPressModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.long_caps);
		ButtonPressMode enumLong = ButtonPressMode.valueForString(example);
		example = mContext.getString(R.string.short_caps);
		ButtonPressMode enumShort = ButtonPressMode.valueForString(example);
		
		assertNotNull("LONG returned null", enumLong);
		assertNotNull("SHORT returned null", enumShort);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    ButtonPressMode temp = ButtonPressMode.valueForString(example);
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
		    ButtonPressMode temp = ButtonPressMode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of ButtonPressMode.
	 */
	public void testListEnum() {
 		List<ButtonPressMode> enumValueList = Arrays.asList(ButtonPressMode.values());

		List<ButtonPressMode> enumTestList = new ArrayList<ButtonPressMode>();
		enumTestList.add(ButtonPressMode.LONG);
		enumTestList.add(ButtonPressMode.SHORT);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}