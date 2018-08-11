package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.LightStatus;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.enums.LightStatus}
 */
public class LightStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "ON";
		LightStatus enumOn = LightStatus.valueForString(example);
		example = "OFF";
		LightStatus enumOff = LightStatus.valueForString(example);
		example = "RAMP_UP";
		LightStatus enumRampUp = LightStatus.valueForString(example);
		example = "RAMP_DOWN";
		LightStatus enumRampDown = LightStatus.valueForString(example);
		example = "UNKNOWN";
		LightStatus enumUnknown = LightStatus.valueForString(example);
		example = "INVALID";
		LightStatus enumInvalid = LightStatus.valueForString(example);

		assertNotNull("ON returned null", enumOn);
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("RAMP_UP returned null", enumRampUp);
		assertNotNull("RAMP_DOWN returned null", enumRampDown);
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("INVALID returned null", enumInvalid);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "oN";
		try {
			LightStatus temp = LightStatus.valueForString(example);
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
			LightStatus temp = LightStatus.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of LightStatus.
	 */
	public void testListEnum() {
		List<LightStatus> enumValueList = Arrays.asList(LightStatus.values());

		List<LightStatus> enumTestList = new ArrayList<LightStatus>();
		enumTestList.add(LightStatus.ON);
		enumTestList.add(LightStatus.OFF);
		enumTestList.add(LightStatus.RAMP_UP);
		enumTestList.add(LightStatus.RAMP_DOWN);
		enumTestList.add(LightStatus.UNKNOWN);
		enumTestList.add(LightStatus.INVALID);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}