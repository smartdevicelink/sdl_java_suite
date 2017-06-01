package com.smartdevicelink.test.rpc.enums;

import android.content.res.Resources;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ButtonEventMode}
 */
public class ButtonEventModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.button_up_caps);
		ButtonEventMode enumButtonUp = ButtonEventMode.valueForString(example);
		example = mContext.getString(R.string.button_down_caps);
		ButtonEventMode enumButtonDown = ButtonEventMode.valueForString(example);
		
		assertNotNull("BUTTONUP returned null", enumButtonUp);
		assertNotNull("BUTTONDOWN returned null", enumButtonDown);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
			ButtonEventMode temp = ButtonEventMode.valueForString(example);
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
		    ButtonEventMode temp = ButtonEventMode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of ButtonEventMode.
	 */
	public void testListEnum() {
 		List<ButtonEventMode> enumValueList = Arrays.asList(ButtonEventMode.values());

		List<ButtonEventMode> enumTestList = new ArrayList<ButtonEventMode>();
		enumTestList.add(ButtonEventMode.BUTTONUP);
		enumTestList.add(ButtonEventMode.BUTTONDOWN);	

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}