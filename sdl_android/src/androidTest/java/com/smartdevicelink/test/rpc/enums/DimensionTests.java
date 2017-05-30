package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.Dimension;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Dimension}
 */
public class DimensionTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.no_fix_caps);
		Dimension enumNoFix = Dimension.valueForString(example);
		example = mContext.getString(R.string.two_d_caps);
		Dimension enum2D = Dimension.valueForString(example);
		example = mContext.getString(R.string.three_d_caps);
		Dimension enum3D = Dimension.valueForString(example);
		
		assertNotNull("NO_FIX returned null", enumNoFix);
		assertNotNull("2D returned null", enum2D);
		assertNotNull("3D returned null", enum3D);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    Dimension temp = Dimension.valueForString(example);
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
		    Dimension temp = Dimension.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	/**
	 * Verifies the possible enum values of Dimension.
	 */
	public void testListEnum() {
 		List<Dimension> enumValueList = Arrays.asList(Dimension.values());

		List<Dimension> enumTestList = new ArrayList<Dimension>();
		enumTestList.add(Dimension.NO_FIX);
		enumTestList.add(Dimension._2D);
		enumTestList.add(Dimension._3D);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}