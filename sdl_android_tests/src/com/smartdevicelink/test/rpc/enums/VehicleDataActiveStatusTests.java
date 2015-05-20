package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataActiveStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataActiveStatus}
 */
public class VehicleDataActiveStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "INACTIVE_NOT_CONFIRMED";
		VehicleDataActiveStatus enumInactiveNotConfirmed = VehicleDataActiveStatus.valueForString(example);
		example = "INACTIVE_CONFIRMED";
		VehicleDataActiveStatus enumInactiveConfirmed = VehicleDataActiveStatus.valueForString(example);
		example = "ACTIVE_NOT_CONFIRMED";
		VehicleDataActiveStatus enumActiveNotConfirmed = VehicleDataActiveStatus.valueForString(example);
		example = "ACTIVE_CONFIRMED";
		VehicleDataActiveStatus enumActiveConfirmed = VehicleDataActiveStatus.valueForString(example);
		example = "FAULT";
		VehicleDataActiveStatus enumFault = VehicleDataActiveStatus.valueForString(example);
		
		assertNotNull("INACTIVE_NOT_CONFIRMED returned null", enumInactiveNotConfirmed);
		assertNotNull("INACTIVE_CONFIRMED returned null", enumInactiveConfirmed);
		assertNotNull("ACTIVE_NOT_CONFIRMED returned null", enumActiveNotConfirmed);
		assertNotNull("ACTIVE_CONFIRMED returned null", enumActiveConfirmed);
		assertNotNull("FAULT returned null", enumFault);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "InACtivE_NoT_ConFIRmED";
		try {
		    VehicleDataActiveStatus temp = VehicleDataActiveStatus.valueForString(example);
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
		    VehicleDataActiveStatus temp = VehicleDataActiveStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of VehicleDataActiveStatus.
	 */
	public void testListEnum() {
 		List<VehicleDataActiveStatus> enumValueList = Arrays.asList(VehicleDataActiveStatus.values());

		List<VehicleDataActiveStatus> enumTestList = new ArrayList<VehicleDataActiveStatus>();
		enumTestList.add(VehicleDataActiveStatus.INACTIVE_NOT_CONFIRMED);
		enumTestList.add(VehicleDataActiveStatus.INACTIVE_CONFIRMED);
		enumTestList.add(VehicleDataActiveStatus.ACTIVE_NOT_CONFIRMED);
		enumTestList.add(VehicleDataActiveStatus.ACTIVE_CONFIRMED);
		enumTestList.add(VehicleDataActiveStatus.FAULT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}