package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

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
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    PowerModeQualificationStatus temp = PowerModeQualificationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}