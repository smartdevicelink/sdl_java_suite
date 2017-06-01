package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SoftButtonType}
 */
public class SoftButtonTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.text_caps);
		SoftButtonType enumText = SoftButtonType.valueForString(example);
		example = mContext.getString(R.string.image_caps);
		SoftButtonType enumImage = SoftButtonType.valueForString(example);
		example = mContext.getString(R.string.both_caps);
		SoftButtonType enumBoth = SoftButtonType.valueForString(example);
		
		assertNotNull("TEXT returned null", enumText);
		assertNotNull("IMAGE returned null", enumImage);
		assertNotNull("BOTH returned null", enumBoth);
	}


	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    SoftButtonType temp = SoftButtonType.valueForString(example);
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
		    SoftButtonType temp = SoftButtonType.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies the possible enum values of SoftButtonType.
	 */
	public void testListEnum() {
 		List<SoftButtonType> enumValueList = Arrays.asList(SoftButtonType.values());

		List<SoftButtonType> enumTestList = new ArrayList<SoftButtonType>();
		enumTestList.add(SoftButtonType.SBT_TEXT);
		enumTestList.add(SoftButtonType.SBT_IMAGE);
		enumTestList.add(SoftButtonType.SBT_BOTH);
		
		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}