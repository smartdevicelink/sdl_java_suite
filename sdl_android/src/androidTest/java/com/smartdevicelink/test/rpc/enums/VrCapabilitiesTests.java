package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VrCapabilities}
 */
public class VrCapabilitiesTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.text);
		VrCapabilities enumText = VrCapabilities.valueForString(example);
		
		assertNotNull("Text returned null", enumText);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    VrCapabilities temp = VrCapabilities.valueForString(example);
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
		    VrCapabilities temp = VrCapabilities.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of VrCapabilities.
	 */
	@SuppressWarnings("deprecation")
	public void testListEnum() {
 		List<VrCapabilities> enumValueList = Arrays.asList(VrCapabilities.values());

		List<VrCapabilities> enumTestList = new ArrayList<VrCapabilities>();
		enumTestList.add(VrCapabilities.TEXT);
		enumTestList.add(VrCapabilities.Text);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}