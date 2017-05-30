package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataResultCode}
 */
public class VehicleDataResultCodeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.success_caps);
		VehicleDataResultCode enumSuccess = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.truncated_data_caps);
		VehicleDataResultCode enumTruncData = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.disallowed_caps);
		VehicleDataResultCode enumDisallowed = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.user_disallowed_caps);
		VehicleDataResultCode enumUserDisallowed = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.invalid_id_caps);
		VehicleDataResultCode enumInvalidId = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_not_available_caps);
		VehicleDataResultCode enumVehicleDataNotAvailable = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.data_already_subscribed);
		VehicleDataResultCode enumDataAlreadySubscribed = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.data_not_subscribed);
		VehicleDataResultCode enumDataNotSubscribed = VehicleDataResultCode.valueForString(example);
		example = mContext.getString(R.string.ignored_caps);
		VehicleDataResultCode enumIgnored = VehicleDataResultCode.valueForString(example);
		
		assertNotNull("SUCCESS returned null", enumSuccess);
		assertNotNull("TRUNCATED_DATA returned null", enumTruncData);
		assertNotNull("DISALLOWED returned null", enumDisallowed);
		assertNotNull("USER_DISALLOWED returned null", enumUserDisallowed);
		assertNotNull("INVALID_ID returned null", enumInvalidId);
		assertNotNull("VEHICLE_DATA_NOT_AVAILABLE returned null", enumVehicleDataNotAvailable);
		assertNotNull("DATA_ALREADY_SUBSCRIBED returned null", enumDataAlreadySubscribed);
		assertNotNull("DATA_NOT_SUBSCRIBED returned null", enumDataNotSubscribed);
		assertNotNull("IGNORED returned null", enumIgnored);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    VehicleDataResultCode temp = VehicleDataResultCode.valueForString(example);
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
		    VehicleDataResultCode temp = VehicleDataResultCode.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	
	
	/**
	 * Verifies the possible enum values of VehicleDataResultCode.
	 */
	public void testListEnum() {
 		List<VehicleDataResultCode> enumValueList = Arrays.asList(VehicleDataResultCode.values());

		List<VehicleDataResultCode> enumTestList = new ArrayList<VehicleDataResultCode>();
		enumTestList.add(VehicleDataResultCode.SUCCESS);
		enumTestList.add(VehicleDataResultCode.TRUNCATED_DATA);
		enumTestList.add(VehicleDataResultCode.DISALLOWED);
		enumTestList.add(VehicleDataResultCode.USER_DISALLOWED);
		enumTestList.add(VehicleDataResultCode.INVALID_ID);
		enumTestList.add(VehicleDataResultCode.VEHICLE_DATA_NOT_AVAILABLE);
		enumTestList.add(VehicleDataResultCode.DATA_ALREADY_SUBSCRIBED);		
		enumTestList.add(VehicleDataResultCode.DATA_NOT_SUBSCRIBED);
		enumTestList.add(VehicleDataResultCode.IGNORED);	

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}