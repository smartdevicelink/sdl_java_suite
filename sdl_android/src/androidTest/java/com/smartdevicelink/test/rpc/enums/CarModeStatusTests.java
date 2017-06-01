package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CarModeStatus}
 */
public class CarModeStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.normal_caps);
		CarModeStatus enumNormal = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.factory_caps);
		CarModeStatus enumFactory = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.transport_caps);
		CarModeStatus enumTransport = CarModeStatus.valueForString(example);
		example = mContext.getString(R.string.crash_caps);
		CarModeStatus enumCrash = CarModeStatus.valueForString(example);
		
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("FACTORY returned null", enumFactory);
		assertNotNull("TRANSPORT returned null", enumTransport);
		assertNotNull("CRASH returned null", enumCrash);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    CarModeStatus temp = CarModeStatus.valueForString(example);
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
		    CarModeStatus temp = CarModeStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of CarModeStatus.
	 */
	public void testListEnum() {
 		List<CarModeStatus> enumValueList = Arrays.asList(CarModeStatus.values());

		List<CarModeStatus> enumTestList = new ArrayList<CarModeStatus>();
		enumTestList.add(CarModeStatus.NORMAL);
		enumTestList.add(CarModeStatus.FACTORY);
		enumTestList.add(CarModeStatus.TRANSPORT);
		enumTestList.add(CarModeStatus.CRASH);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}