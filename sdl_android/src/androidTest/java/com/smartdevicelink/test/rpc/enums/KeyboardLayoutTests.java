package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.KeyboardLayout}
 */
public class KeyboardLayoutTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.qwerty_caps);
		KeyboardLayout enumQwerty = KeyboardLayout.valueForString(example);
		example = mContext.getString(R.string.qwertz_caps);
		KeyboardLayout enumQwertz = KeyboardLayout.valueForString(example);
		example = mContext.getString(R.string.azerty_caps);
		KeyboardLayout enumAzerty = KeyboardLayout.valueForString(example);
		
		assertNotNull("QWERTY returned null", enumQwerty);
		assertNotNull("QWERTZ returned null", enumQwertz);
		assertNotNull("AZERTY returned null", enumAzerty);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    KeyboardLayout temp = KeyboardLayout.valueForString(example);
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
		    KeyboardLayout temp = KeyboardLayout.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of KeyboardLayout.
	 */
	public void testListEnum() {
 		List<KeyboardLayout> enumValueList = Arrays.asList(KeyboardLayout.values());

		List<KeyboardLayout> enumTestList = new ArrayList<KeyboardLayout>();
		enumTestList.add(KeyboardLayout.QWERTY);
		enumTestList.add(KeyboardLayout.QWERTZ);
		enumTestList.add(KeyboardLayout.AZERTY);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}