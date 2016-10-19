package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataNotificationStatus}
 */
public class VehicleDataNotificationStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NOT_SUPPORTED";
		VehicleDataNotificationStatus enumNotSupported = VehicleDataNotificationStatus.valueForString(example);
		example = "NORMAL";
		VehicleDataNotificationStatus enumNormal = VehicleDataNotificationStatus.valueForString(example);
		example = "ACTIVE";
		VehicleDataNotificationStatus enumActive = VehicleDataNotificationStatus.valueForString(example);
		example = "NOT_USED";
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
		String example = "nOT_SuppOrTEd";
		try {
		    VehicleDataNotificationStatus temp = VehicleDataNotificationStatus.valueForString(example);
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
		    VehicleDataNotificationStatus temp = VehicleDataNotificationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}