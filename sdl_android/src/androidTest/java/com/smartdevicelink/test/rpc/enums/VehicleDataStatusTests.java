package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataStatus}
 */
public class VehicleDataStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.no_data_exists_caps);
		VehicleDataStatus enumNoDataExists = VehicleDataStatus.valueForString(example);
		example = mContext.getString(R.string.off_caps);
		VehicleDataStatus enumOff = VehicleDataStatus.valueForString(example);
		example = mContext.getString(R.string.on_caps);
		VehicleDataStatus enumOn = VehicleDataStatus.valueForString(example);
		
		assertNotNull("NO_DATA_EXISTS returned null", enumNoDataExists);
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    VehicleDataStatus temp = VehicleDataStatus.valueForString(example);
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
		    VehicleDataStatus temp = VehicleDataStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of VehicleDataStatus.
	 */
	public void testListEnum() {
 		List<VehicleDataStatus> enumValueList = Arrays.asList(VehicleDataStatus.values());

		List<VehicleDataStatus> enumTestList = new ArrayList<VehicleDataStatus>();
		enumTestList.add(VehicleDataStatus.NO_DATA_EXISTS);
		enumTestList.add(VehicleDataStatus.OFF);
		enumTestList.add(VehicleDataStatus.ON);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}