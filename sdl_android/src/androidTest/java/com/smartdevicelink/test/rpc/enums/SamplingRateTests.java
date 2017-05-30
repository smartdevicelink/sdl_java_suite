package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SamplingRate}
 */
public class SamplingRateTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.eight_khz);
		SamplingRate enum8Khz = SamplingRate.valueForString(example);
		example = mContext.getString(R.string.sixteen_khz);
		SamplingRate enum16Khz = SamplingRate.valueForString(example);
		example = mContext.getString(R.string.twentytwo_khz);
		SamplingRate enum22Khz = SamplingRate.valueForString(example);
		example = mContext.getString(R.string.fourtyfour_khz);
		SamplingRate enum44Khz = SamplingRate.valueForString(example);
		
		assertNotNull("8KHZ returned null", enum8Khz);
		assertNotNull("16KHZ returned null", enum16Khz);
		assertNotNull("22KHZ returned null", enum22Khz);
		assertNotNull("44KHZ returned null", enum44Khz);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    SamplingRate temp = SamplingRate.valueForString(example);
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
		    SamplingRate temp = SamplingRate.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies the possible enum values of SamplingRate.
	 */
	public void testListEnum() {
 		List<SamplingRate> enumValueList = Arrays.asList(SamplingRate.values());

		List<SamplingRate> enumTestList = new ArrayList<SamplingRate>();
		enumTestList.add(SamplingRate._8KHZ);
		enumTestList.add(SamplingRate._16KHZ);
		enumTestList.add(SamplingRate._22KHZ);
		enumTestList.add(SamplingRate._44KHZ);
		
		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}