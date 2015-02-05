package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;

public class VehicleDataNotificationStatusTests extends TestCase {

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
	
	public void testInvalidEnum () {
		String example = "nOT_SuppOrTEd";
		try {
			VehicleDataNotificationStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			VehicleDataNotificationStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
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
