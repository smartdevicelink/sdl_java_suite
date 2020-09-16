package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.DistanceUnit;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.DistanceUnit}
 */
public class DistanceUnitTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "MILES";
		DistanceUnit enumMiles = DistanceUnit.valueForString(example);
		example = "KILOMETERS";
		DistanceUnit enumKilometers = DistanceUnit.valueForString(example);

		assertNotNull("MILES returned null", enumMiles);
		assertNotNull("KILOMETERS returned null", enumKilometers);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "mILES";
		try {
			DistanceUnit temp = DistanceUnit.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum() {
		String example = null;
		try {
			DistanceUnit temp = DistanceUnit.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of DistanceUnit.
	 */
	public void testListEnum() {
		List<DistanceUnit> enumValueList = Arrays.asList(DistanceUnit.values());
		List<DistanceUnit> enumTestList = new ArrayList<DistanceUnit>();
		enumTestList.add(DistanceUnit.MILES);
		enumTestList.add(DistanceUnit.KILOMETERS);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}