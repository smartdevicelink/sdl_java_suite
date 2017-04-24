package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleDataStatus}
 */
public class VehicleDataStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NO_DATA_EXISTS";
		VehicleDataStatus enumNoDataExists = VehicleDataStatus.valueForString(example);
		example = "OFF";
		VehicleDataStatus enumOff = VehicleDataStatus.valueForString(example);
		example = "ON";
		VehicleDataStatus enumOn = VehicleDataStatus.valueForString(example);
		
		assertNotNull("NO_DATA_EXISTS returned null", enumNoDataExists);
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "No_DatA_ExiSTs";
		try {
		    VehicleDataStatus temp = VehicleDataStatus.valueForString(example);
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
		    VehicleDataStatus temp = VehicleDataStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}