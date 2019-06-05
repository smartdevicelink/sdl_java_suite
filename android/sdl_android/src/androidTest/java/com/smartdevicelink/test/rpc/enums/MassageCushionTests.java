package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.MassageCushion;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.rpc.enums.MassageCushion}
 */
public class MassageCushionTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "TOP_LUMBAR";
		MassageCushion enumTopLumbar = MassageCushion.valueForString(example);
		example = "MIDDLE_LUMBAR";
		MassageCushion enumMiddleLumbar = MassageCushion.valueForString(example);
		example = "BOTTOM_LUMBAR";
		MassageCushion enumBottomLumbar = MassageCushion.valueForString(example);
		example = "BACK_BOLSTERS";
		MassageCushion enumBackBolsters = MassageCushion.valueForString(example);
		example = "SEAT_BOLSTERS";
		MassageCushion enumSeatBolsters = MassageCushion.valueForString(example);

		assertNotNull("TOP_LUMBAR returned null", enumTopLumbar);
		assertNotNull("MIDDLE_LUMBAR returned null", enumMiddleLumbar);
		assertNotNull("BOTTOM_LUMBAR returned null", enumBottomLumbar);
		assertNotNull("BACK_BOLSTERS returned null", enumBackBolsters);
		assertNotNull("SEAT_BOLSTERS returned null", enumSeatBolsters);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "tOP_LUMBAR";
		try {
			MassageCushion temp = MassageCushion.valueForString(example);
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
			MassageCushion temp = MassageCushion.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of MassageCushion.
	 */
	public void testListEnum() {
		List<MassageCushion> enumValueList = Arrays.asList(MassageCushion.values());

		List<MassageCushion> enumTestList = new ArrayList<MassageCushion>();
		enumTestList.add(MassageCushion.TOP_LUMBAR);
		enumTestList.add(MassageCushion.MIDDLE_LUMBAR);
		enumTestList.add(MassageCushion.BOTTOM_LUMBAR);
		enumTestList.add(MassageCushion.BACK_BOLSTERS);
		enumTestList.add(MassageCushion.SEAT_BOLSTERS);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}