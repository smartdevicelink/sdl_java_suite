package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.MassageZone;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.MassageZone}
 */
public class MassageZoneTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "LUMBAR";
		MassageZone enumLumbar = MassageZone.valueForString(example);
		example = "SEAT_CUSHION";
		MassageZone enumSeatCushion = MassageZone.valueForString(example);

		assertNotNull("LUMBAR returned null", enumLumbar);
		assertNotNull("SEAT_CUSHION returned null", enumSeatCushion);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "lUMBAR";
		try {
			MassageZone temp = MassageZone.valueForString(example);
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
			MassageZone temp = MassageZone.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of MassageZone.
	 */
	public void testListEnum() {
		List<MassageZone> enumValueList = Arrays.asList(MassageZone.values());

		List<MassageZone> enumTestList = new ArrayList<MassageZone>();
		enumTestList.add(MassageZone.LUMBAR);
		enumTestList.add(MassageZone.SEAT_CUSHION);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}