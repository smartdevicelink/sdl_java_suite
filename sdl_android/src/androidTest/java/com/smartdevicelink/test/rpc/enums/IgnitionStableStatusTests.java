package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.IgnitionStableStatus}
 */
public class IgnitionStableStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.ignite_switch_not_stable_caps);
		IgnitionStableStatus enumIgnitionSwitchNotStable = IgnitionStableStatus.valueForString(example);
		example = mContext.getString(R.string.ingite_switch_stable_caps);
		IgnitionStableStatus enumIgnitionSwitchStable = IgnitionStableStatus.valueForString(example);
		example = mContext.getString(R.string.missing_from_trans_caps);
		IgnitionStableStatus enumMissingFromTransmitter = IgnitionStableStatus.valueForString(example);
		
		assertNotNull("IGNITION_SWITCH_NOT_STABLE returned null", enumIgnitionSwitchNotStable);
		assertNotNull("IGNITION_SWITCH_STABLE returned null", enumIgnitionSwitchStable);
		assertNotNull("MISSING_FROM_TRANSMITTER returned null", enumMissingFromTransmitter);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    IgnitionStableStatus temp = IgnitionStableStatus.valueForString(example);
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
		    IgnitionStableStatus temp = IgnitionStableStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of IgnitionStableStatus.
	 */
	public void testListEnum() {
 		List<IgnitionStableStatus> enumValueList = Arrays.asList(IgnitionStableStatus.values());

		List<IgnitionStableStatus> enumTestList = new ArrayList<IgnitionStableStatus>();
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_NOT_STABLE);
		enumTestList.add(IgnitionStableStatus.IGNITION_SWITCH_STABLE);
		enumTestList.add(IgnitionStableStatus.MISSING_FROM_TRANSMITTER);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}