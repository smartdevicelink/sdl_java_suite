package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.PowerModeQualificationStatus}
 */
public class PowerModeQualificationStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.power_mode_undef_caps);
		PowerModeQualificationStatus enumPowerModeUndefined = PowerModeQualificationStatus.valueForString(example);
		example = mContext.getString(R.string.power_mode_eval_caps);
		PowerModeQualificationStatus enumPowerModeEvaluationInProgress = PowerModeQualificationStatus.valueForString(example);
		example = mContext.getString(R.string.not_defined_caps);
		PowerModeQualificationStatus enumNotDefined = PowerModeQualificationStatus.valueForString(example);
		example = mContext.getString(R.string.power_mode_on_caps);
		PowerModeQualificationStatus enumPowerModeOk = PowerModeQualificationStatus.valueForString(example);
		
		assertNotNull("POWER_MODE_UNDEFINED returned null", enumPowerModeUndefined);
		assertNotNull("POWER_MODE_EVALUATION_IN_PROGRESS returned null", enumPowerModeEvaluationInProgress);
		assertNotNull("NOT_DEFINED returned null", enumNotDefined);
		assertNotNull("POWER_MODE_OK returned null", enumPowerModeOk);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    PowerModeQualificationStatus temp = PowerModeQualificationStatus.valueForString(example);
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
		    PowerModeQualificationStatus temp = PowerModeQualificationStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of PowerModeQualificationStatus.
	 */
	public void testListEnum() {
 		List<PowerModeQualificationStatus> enumValueList = Arrays.asList(PowerModeQualificationStatus.values());

		List<PowerModeQualificationStatus> enumTestList = new ArrayList<PowerModeQualificationStatus>();
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_UNDEFINED);
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_EVALUATION_IN_PROGRESS);
		enumTestList.add(PowerModeQualificationStatus.NOT_DEFINED);
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_OK);	

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}