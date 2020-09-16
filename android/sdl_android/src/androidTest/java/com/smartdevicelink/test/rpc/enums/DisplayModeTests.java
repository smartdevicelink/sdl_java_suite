package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.DisplayMode;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.DisplayMode}
 */
public class DisplayModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "DAY";
		DisplayMode enumDay = DisplayMode.valueForString(example);
		example = "NIGHT";
		DisplayMode enumNight = DisplayMode.valueForString(example);
		example = "AUTO";
		DisplayMode enumAuto = DisplayMode.valueForString(example);

		assertNotNull("DAY returned null", enumDay);
		assertNotNull("NIGHT returned null", enumNight);
		assertNotNull("AUTO returned null", enumAuto);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "dAY";
		try {
			DisplayMode temp = DisplayMode.valueForString(example);
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
			DisplayMode temp = DisplayMode.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of DisplayMode.
	 */
	public void testListEnum() {
		List<DisplayMode> enumValueList = Arrays.asList(DisplayMode.values());

		List<DisplayMode> enumTestList = new ArrayList<DisplayMode>();
		enumTestList.add(DisplayMode.DAY);
		enumTestList.add(DisplayMode.NIGHT);
		enumTestList.add(DisplayMode.AUTO);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}