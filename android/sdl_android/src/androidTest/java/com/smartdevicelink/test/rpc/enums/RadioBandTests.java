package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.RadioBand;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.enums.RadioBand}
 */
public class RadioBandTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "AM";
		RadioBand enumAm = RadioBand.valueForString(example);
		example = "FM";
		RadioBand enumFm = RadioBand.valueForString(example);
		example = "XM";
		RadioBand enumXm = RadioBand.valueForString(example);

		assertNotNull("AM returned null", enumAm);
		assertNotNull("FM returned null", enumFm);
		assertNotNull("XM returned null", enumXm);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "aM";
		try {
			RadioBand temp = RadioBand.valueForString(example);
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
			RadioBand temp = RadioBand.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of RadioBand.
	 */
	public void testListEnum() {
 		List<RadioBand> enumValueList = Arrays.asList(RadioBand.values());

		List<RadioBand> enumTestList = new ArrayList<RadioBand>();
		enumTestList.add(RadioBand.AM);
		enumTestList.add(RadioBand.FM);
		enumTestList.add(RadioBand.XM);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}