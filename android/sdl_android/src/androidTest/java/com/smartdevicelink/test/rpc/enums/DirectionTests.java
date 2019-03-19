package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.Direction;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.Direction}
 */
public class DirectionTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {
		String example = "LEFT";
		Direction enumLeft = Direction.valueForString(example);
		example = "RIGHT";
		Direction enumRight = Direction.valueForString(example);

		assertNotNull("LEFT returned null", enumLeft);
		assertNotNull("RIGHT returned null", enumRight);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "fRONT";
		try {
			Direction temp = Direction.valueForString(example);
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
			Direction temp = Direction.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of Direction.
	 */
	public void testListEnum() {
		List<Direction> enumValueList = Arrays.asList(Direction.values());

		List<Direction> enumTestList = new ArrayList<>();
		enumTestList.add(Direction.LEFT);
		enumTestList.add(Direction.RIGHT);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}
