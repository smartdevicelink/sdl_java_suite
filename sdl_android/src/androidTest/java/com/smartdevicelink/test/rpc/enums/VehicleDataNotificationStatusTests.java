package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataNotificationStatus}
 */
public class VehicleDataNotificationStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.not_supported_caps);
		VehicleDataNotificationStatus enumNotSupported = VehicleDataNotificationStatus.valueForString(example);
		example = mContext.getString(R.string.normal_caps);
		VehicleDataNotificationStatus enumNormal = VehicleDataNotificationStatus.valueForString(example);
		example = mContext.getString(R.string.active_caps);
		VehicleDataNotificationStatus enumActive = VehicleDataNotificationStatus.valueForString(example);
		example = mContext.getString(R.string.not_used_caps);
		VehicleDataNotificationStatus enumNotUsed = VehicleDataNotificationStatus.valueForString(example);
		
		assertNotNull("NOT_SUPPORTED returned null", enumNotSupported);
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("ACTIVE returned null", enumActive);
		assertNotNull("NOT_USED returned null", enumNotUsed);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    VehicleDataNotificationStatus temp = VehicleDataNotificationStatus.valueForString(example);
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
		    VehicleDataNotificationStatus temp = VehicleDataNotificationStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of VehicleDataNotification.
	 */
	public void testListEnum() {
 		List<VehicleDataNotificationStatus> enumValueList = Arrays.asList(VehicleDataNotificationStatus.values());

		List<VehicleDataNotificationStatus> enumTestList = new ArrayList<VehicleDataNotificationStatus>();
		enumTestList.add(VehicleDataNotificationStatus.NOT_SUPPORTED);
		enumTestList.add(VehicleDataNotificationStatus.NORMAL);
		enumTestList.add(VehicleDataNotificationStatus.ACTIVE);
		enumTestList.add(VehicleDataNotificationStatus.NOT_USED);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}