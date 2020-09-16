package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.ElectronicParkBrakeStatus}
 */
public class ElectronicParkBrakeStatusTests extends TestCase {
	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "CLOSED";
		ElectronicParkBrakeStatus enumClosed = ElectronicParkBrakeStatus.valueForString(example);
		example = "TRANSITION";
		ElectronicParkBrakeStatus enumTransition = ElectronicParkBrakeStatus.valueForString(example);
		example = "OPEN";
		ElectronicParkBrakeStatus enumOpen = ElectronicParkBrakeStatus.valueForString(example);
		example = "DRIVE_ACTIVE";
		ElectronicParkBrakeStatus enumDriveActive = ElectronicParkBrakeStatus.valueForString(example);
		example = "FAULT";
		ElectronicParkBrakeStatus enumFault = ElectronicParkBrakeStatus.valueForString(example);

		assertNotNull("CLOSED returned null", enumClosed);
		assertNotNull("TRANSITION returned null", enumTransition);
		assertNotNull("OPEN returned null", enumOpen);
		assertNotNull("DRIVE_ACTIVE returned null", enumDriveActive);
		assertNotNull("FAULT returned null", enumFault);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "Clsoed";
		try {
			ElectronicParkBrakeStatus temp = ElectronicParkBrakeStatus.valueForString(example);
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
			ElectronicParkBrakeStatus temp = ElectronicParkBrakeStatus.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of Electronic Brake Status.
	 */
	public void testListEnum() {
		List<ElectronicParkBrakeStatus> enumValueList = Arrays.asList(ElectronicParkBrakeStatus.values());

		List<ElectronicParkBrakeStatus> enumTestList = new ArrayList<ElectronicParkBrakeStatus>();
		enumTestList.add(ElectronicParkBrakeStatus.CLOSED);
		enumTestList.add(ElectronicParkBrakeStatus.TRANSITION);
		enumTestList.add(ElectronicParkBrakeStatus.OPEN);
		enumTestList.add(ElectronicParkBrakeStatus.DRIVE_ACTIVE);
		enumTestList.add(ElectronicParkBrakeStatus.FAULT);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}
