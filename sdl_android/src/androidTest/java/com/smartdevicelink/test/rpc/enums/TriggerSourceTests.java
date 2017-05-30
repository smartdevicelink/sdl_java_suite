package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TriggerSource}
 */
public class TriggerSourceTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.menu_caps);
		TriggerSource enumMenu = TriggerSource.valueForString(example);
		example = mContext.getString(R.string.vr_caps);
		TriggerSource enumVr = TriggerSource.valueForString(example);
		example = mContext.getString(R.string.keyboard_caps);
		TriggerSource enumKeyboard = TriggerSource.valueForString(example);
		
		assertNotNull("MENU returned null", enumMenu);
		assertNotNull("VR returned null", enumVr);
		assertNotNull("KEYBOARD returned null", enumKeyboard);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    TriggerSource temp = TriggerSource.valueForString(example);
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
		    TriggerSource temp = TriggerSource.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	

	/**
	 * Verifies the possible enum values of TriggerSource.
	 */
	public void testListEnum() {
 		List<TriggerSource> enumValueList = Arrays.asList(TriggerSource.values());

		List<TriggerSource> enumTestList = new ArrayList<TriggerSource>();
		enumTestList.add(TriggerSource.TS_MENU);
		enumTestList.add(TriggerSource.TS_VR);
		enumTestList.add(TriggerSource.TS_KEYBOARD);
		
		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}