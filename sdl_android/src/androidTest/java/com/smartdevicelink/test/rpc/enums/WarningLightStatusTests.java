package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.WarningListStatus}
 */
public class WarningLightStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.off_caps);
		WarningLightStatus enumOff = WarningLightStatus.valueForString(example);
		example = mContext.getString(R.string.on_caps);
		WarningLightStatus enumOn = WarningLightStatus.valueForString(example);
		example = mContext.getString(R.string.flash_caps);
		WarningLightStatus enumFlash = WarningLightStatus.valueForString(example);
		example = mContext.getString(R.string.not_used_caps);
		WarningLightStatus enumNotUsed = WarningLightStatus.valueForString(example);
		
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
		assertNotNull("FLASH returned null", enumFlash);
		assertNotNull("NOT_USED returned null", enumNotUsed);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    WarningLightStatus temp = WarningLightStatus.valueForString(example);
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
		    WarningLightStatus temp = WarningLightStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of WarningLightStatus.
	 */
	public void testListEnum() {
 		List<WarningLightStatus> enumValueList = Arrays.asList(WarningLightStatus.values());

		List<WarningLightStatus> enumTestList = new ArrayList<WarningLightStatus>();
		enumTestList.add(WarningLightStatus.OFF);
		enumTestList.add(WarningLightStatus.ON);
		enumTestList.add(WarningLightStatus.FLASH);
		enumTestList.add(WarningLightStatus.NOT_USED);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}