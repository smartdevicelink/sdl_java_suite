package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.MassageMode;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.MassageMode}
 */
public class MassageModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "OFF";
		MassageMode enumOff = MassageMode.valueForString(example);
		example = "LOW";
		MassageMode enumLow = MassageMode.valueForString(example);
		example = "HIGH";
		MassageMode enumHigh = MassageMode.valueForString(example);

		assertNotNull("OFF returned null", enumOff);
		assertNotNull("LOW returned null", enumLow);
		assertNotNull("HIGH returned null", enumHigh);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "oFF";
		try {
			MassageMode temp = MassageMode.valueForString(example);
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
			MassageMode temp = MassageMode.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of MassageMode.
	 */
	public void testListEnum() {
		List<MassageMode> enumValueList = Arrays.asList(MassageMode.values());

		List<MassageMode> enumTestList = new ArrayList<MassageMode>();
		enumTestList.add(MassageMode.OFF);
		enumTestList.add(MassageMode.LOW);
		enumTestList.add(MassageMode.HIGH);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}