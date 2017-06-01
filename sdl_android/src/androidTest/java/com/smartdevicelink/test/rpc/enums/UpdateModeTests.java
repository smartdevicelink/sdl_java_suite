package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.UpdateMode}
 */
public class UpdateModeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.countup_caps);
		UpdateMode enumCountUp = UpdateMode.valueForString(example);
		example = mContext.getString(R.string.countdown_caps);
		UpdateMode enumCountDown = UpdateMode.valueForString(example);
		example = mContext.getString(R.string.pause_caps);
		UpdateMode enumPause = UpdateMode.valueForString(example);
		example = mContext.getString(R.string.resume_caps);
		UpdateMode enumResume = UpdateMode.valueForString(example);
		example = mContext.getString(R.string.clear_caps);
		UpdateMode enumClear = UpdateMode.valueForString(example);
		
		assertNotNull("COUNTUP returned null", enumCountUp);
		assertNotNull("COUNTDOWN returned null", enumCountDown);
		assertNotNull("PAUSE returned null", enumPause);
		assertNotNull("RESUME returned null", enumResume);
		assertNotNull("CLEAR returned null", enumClear);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    UpdateMode temp = UpdateMode.valueForString(example);
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
		    UpdateMode temp = UpdateMode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of UpdateMode.
	 */
	public void testListEnum() {
 		List<UpdateMode> enumValueList = Arrays.asList(UpdateMode.values());

		List<UpdateMode> enumTestList = new ArrayList<UpdateMode>();
		enumTestList.add(UpdateMode.COUNTUP);
		enumTestList.add(UpdateMode.COUNTDOWN);
		enumTestList.add(UpdateMode.PAUSE);
		enumTestList.add(UpdateMode.RESUME);
		enumTestList.add(UpdateMode.CLEAR);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}