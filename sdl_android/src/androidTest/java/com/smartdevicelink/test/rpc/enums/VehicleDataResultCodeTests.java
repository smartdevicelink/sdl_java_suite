package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataResultCode}
 */
public class VehicleDataResultCodeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "SUCCESS";
		VehicleDataResultCode enumSuccess = VehicleDataResultCode.valueForString(example);
		example = "TRUNCATED_DATA";
		VehicleDataResultCode enumTruncData = VehicleDataResultCode.valueForString(example);
		example = "DISALLOWED";
		VehicleDataResultCode enumDisallowed = VehicleDataResultCode.valueForString(example);
		example = "USER_DISALLOWED";
		VehicleDataResultCode enumUserDisallowed = VehicleDataResultCode.valueForString(example);
		example = "INVALID_ID";
		VehicleDataResultCode enumInvalidId = VehicleDataResultCode.valueForString(example);
		example = "VEHICLE_DATA_NOT_AVAILABLE";
		VehicleDataResultCode enumVehicleDataNotAvailable = VehicleDataResultCode.valueForString(example);
		example = "DATA_ALREADY_SUBSCRIBED";
		VehicleDataResultCode enumDataAlreadySubscribed = VehicleDataResultCode.valueForString(example);
		example = "DATA_NOT_SUBSCRIBED";
		VehicleDataResultCode enumDataNotSubscribed = VehicleDataResultCode.valueForString(example);
		example = "IGNORED";
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
		String example = "suCcesS";
		try {
		    VehicleDataResultCode temp = VehicleDataResultCode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    VehicleDataResultCode temp = VehicleDataResultCode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}