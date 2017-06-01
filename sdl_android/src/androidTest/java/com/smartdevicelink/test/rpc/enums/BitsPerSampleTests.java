package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.BitsPerSample}
 */
public class BitsPerSampleTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.eight_bit);
		BitsPerSample enum8Bit = BitsPerSample.valueForString(example);
		example = mContext.getString(R.string.sixteen_bit);
		BitsPerSample enum16Bit = BitsPerSample.valueForString(example);
		
		assertNotNull("8_BIT returned null", enum8Bit);
		assertNotNull("16_BIT returned null", enum16Bit);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    BitsPerSample temp = BitsPerSample.valueForString(example);
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
		    BitsPerSample temp = BitsPerSample.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies the possible enum values of BitsPerSample.
	 */
	public void testListEnum() {
 		List<BitsPerSample> enumValueList = Arrays.asList(BitsPerSample.values());

		List<BitsPerSample> enumTestList = new ArrayList<BitsPerSample>();
		enumTestList.add(BitsPerSample._8_BIT);
		enumTestList.add(BitsPerSample._16_BIT);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}