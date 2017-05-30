package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.HmiZoneCapabilities}
 */
public class HmiZoneCapabilitiesTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.front_caps);
		HmiZoneCapabilities enumFront = HmiZoneCapabilities.valueForString(example);
		example = mContext.getString(R.string.back_caps);
		HmiZoneCapabilities enumBack = HmiZoneCapabilities.valueForString(example);
		
		assertNotNull("FRONT returned null", enumFront);
		assertNotNull("BACK returned null", enumBack);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    HmiZoneCapabilities temp = HmiZoneCapabilities.valueForString(example);
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
		    HmiZoneCapabilities temp = HmiZoneCapabilities.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of HmiZoneCapabilities.
	 */
	public void testListEnum() {
 		List<HmiZoneCapabilities> enumValueList = Arrays.asList(HmiZoneCapabilities.values());

		List<HmiZoneCapabilities> enumTestList = new ArrayList<HmiZoneCapabilities>();
		enumTestList.add(HmiZoneCapabilities.FRONT);
		enumTestList.add(HmiZoneCapabilities.BACK);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}