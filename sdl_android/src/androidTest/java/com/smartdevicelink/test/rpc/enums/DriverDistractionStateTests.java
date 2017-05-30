package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.DriverDistractionState}
 */
public class DriverDistractionStateTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.dd_on_caps);
		DriverDistractionState enumDdOn = DriverDistractionState.valueForString(example);
		example = mContext.getString(R.string.dd_off_caps);
		DriverDistractionState enumDdOff = DriverDistractionState.valueForString(example);
		
		assertNotNull("DD_ON returned null", enumDdOn);
		assertNotNull("DD_OFF returned null", enumDdOff);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
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
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of DriverDistractionState.
	 */
	public void testListEnum() {
 		List<DriverDistractionState> enumValueList = Arrays.asList(DriverDistractionState.values());

		List<DriverDistractionState> enumTestList = new ArrayList<DriverDistractionState>();
		enumTestList.add(DriverDistractionState.DD_ON);
		enumTestList.add(DriverDistractionState.DD_OFF);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}