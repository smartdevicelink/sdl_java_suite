package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType}
 */
public class SeatMemoryActionTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums() {
		String example = "SAVE";
		SeatMemoryActionType enumSave = SeatMemoryActionType.valueForString(example);
		example = "RESTORE";
		SeatMemoryActionType enumRestore = SeatMemoryActionType.valueForString(example);
		example = "NONE";
		SeatMemoryActionType enumNone = SeatMemoryActionType.valueForString(example);

		assertNotNull("SAVE returned null", enumSave);
		assertNotNull("RESTORE returned null", enumRestore);
		assertNotNull("NONE returned null", enumNone);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum() {
		String example = "sAVE";
		try {
			SeatMemoryActionType temp = SeatMemoryActionType.valueForString(example);
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
			SeatMemoryActionType temp = SeatMemoryActionType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (NullPointerException exception) {
			fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of SeatMemoryActionType.
	 */
	public void testListEnum() {
		List<SeatMemoryActionType> enumValueList = Arrays.asList(SeatMemoryActionType.values());

		List<SeatMemoryActionType> enumTestList = new ArrayList<SeatMemoryActionType>();
		enumTestList.add(SeatMemoryActionType.SAVE);
		enumTestList.add(SeatMemoryActionType.RESTORE);
		enumTestList.add(SeatMemoryActionType.NONE);

		assertTrue("Enum value list does not match enum class list",
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}