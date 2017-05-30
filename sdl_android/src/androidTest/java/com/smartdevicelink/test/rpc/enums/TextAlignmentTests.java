package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TextAlignment}
 */
public class TextAlignmentTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.left_aligned_caps);
		TextAlignment enumLeftAligned = TextAlignment.valueForString(example);
		example = mContext.getString(R.string.right_aligned_caps);
		TextAlignment enumRightAligned = TextAlignment.valueForString(example);
		example = mContext.getString(R.string.centered_caps);
		TextAlignment enumCentered = TextAlignment.valueForString(example);
		
		assertNotNull("LEFT_ALIGNED returned null", enumLeftAligned);
		assertNotNull("RIGHT_ALIGNED returned null", enumRightAligned);
		assertNotNull("CENTERED returned null", enumCentered);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    TextAlignment temp = TextAlignment.valueForString(example);
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
		    TextAlignment temp = TextAlignment.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of TextAlignment.
	 */
	public void testListEnum() {
 		List<TextAlignment> enumValueList = Arrays.asList(TextAlignment.values());

		List<TextAlignment> enumTestList = new ArrayList<TextAlignment>();
		enumTestList.add(TextAlignment.LEFT_ALIGNED);
		enumTestList.add(TextAlignment.RIGHT_ALIGNED);
		enumTestList.add(TextAlignment.CENTERED);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}