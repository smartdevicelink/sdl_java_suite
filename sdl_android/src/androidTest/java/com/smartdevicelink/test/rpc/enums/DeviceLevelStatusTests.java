package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.DeviceLevelStatus}
 */
public class DeviceLevelStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.zero_level_bars_caps);
		DeviceLevelStatus enumZeroLevel = DeviceLevelStatus.valueForString(example);
		example = mContext.getString(R.string.one_level_bars_caps);
		DeviceLevelStatus enumOneLevel = DeviceLevelStatus.valueForString(example);
		example = mContext.getString(R.string.two_lvl_bars_caps);
		DeviceLevelStatus enumTwoLevel = DeviceLevelStatus.valueForString(example);
		example = mContext.getString(R.string.three_lvl_bars_caps);
		DeviceLevelStatus enumThreeLevel = DeviceLevelStatus.valueForString(example);
		example = mContext.getString(R.string.four_lvl_bars_caps);
		DeviceLevelStatus enumFourLevel = DeviceLevelStatus.valueForString(example);
		example = mContext.getString(R.string.not_prov_caps);
		DeviceLevelStatus enumNotProvided = DeviceLevelStatus.valueForString(example);
				
		assertNotNull("ZERO_LEVEL_BARS returned null", enumZeroLevel);
		assertNotNull("ONE_LEVEL_BARS returned null", enumOneLevel);
		assertNotNull("TWO_LEVEL_BARS returned null", enumTwoLevel);
		assertNotNull("THREE_LEVEL_BARS returned null", enumThreeLevel);
		assertNotNull("FOUR_LEVEL_BARS returned null", enumFourLevel);
		assertNotNull("NOT_PROVIDED returned null", enumNotProvided);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    DeviceLevelStatus temp = DeviceLevelStatus.valueForString(example);
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
		    DeviceLevelStatus temp = DeviceLevelStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of DeviceLevelStatus.
	 */
	public void testListEnum() {
 		List<DeviceLevelStatus> enumValueList = Arrays.asList(DeviceLevelStatus.values());
 		
		List<DeviceLevelStatus> enumTestList = new ArrayList<DeviceLevelStatus>();
		enumTestList.add(DeviceLevelStatus.ZERO_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.ONE_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.TWO_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.THREE_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.FOUR_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.NOT_PROVIDED);		

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}