package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.LockScreenStatus}
 */
public class LockScreenStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.required_caps);
		LockScreenStatus enumRequired = LockScreenStatus.valueForString(example);
		example = mContext.getString(R.string.optional_caps);
		LockScreenStatus enumOptional = LockScreenStatus.valueForString(example);
		example = mContext.getString(R.string.off_caps);
		LockScreenStatus enumOff = LockScreenStatus.valueForString(example);
		
		assertNotNull("REQUIRED returned null", enumRequired);
		assertNotNull("OPTIONAL returned null", enumOptional);
		assertNotNull("OFF returned null", enumOff);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    LockScreenStatus temp = LockScreenStatus.valueForString(example);
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
		    LockScreenStatus temp = LockScreenStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of LockScreenStatus.
	 */
	public void testListEnum() {
 		List<LockScreenStatus> enumValueList = Arrays.asList(LockScreenStatus.values());

		List<LockScreenStatus> enumTestList = new ArrayList<LockScreenStatus>();
		enumTestList.add(LockScreenStatus.REQUIRED);
		enumTestList.add(LockScreenStatus.OPTIONAL);
		enumTestList.add(LockScreenStatus.OFF);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}