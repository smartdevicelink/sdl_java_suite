package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.DefrostZone;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.DefrostZone}
 */
public class DefrostZoneTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "FRONT";
		DefrostZone enumFront = DefrostZone.valueForString(example);
		example = "REAR";
		DefrostZone enumRear = DefrostZone.valueForString(example);
		example = "ALL";
		DefrostZone enumAll = DefrostZone.valueForString(example);
		example = "NONE";
		DefrostZone enumNone = DefrostZone.valueForString(example);
		
		assertNotNull("FRONT returned null", enumFront);
		assertNotNull("REAR returned null", enumRear);
		assertNotNull("ALL returned null", enumAll);
		assertNotNull("NONE returned null", enumNone);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "fRONT";
		try {
			DefrostZone temp = DefrostZone.valueForString(example);
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
			DefrostZone temp = DefrostZone.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of DefrostZone.
	 */
	public void testListEnum() {
 		List<DefrostZone> enumValueList = Arrays.asList(DefrostZone.values());

		List<DefrostZone> enumTestList = new ArrayList<DefrostZone>();
		enumTestList.add(DefrostZone.FRONT);
		enumTestList.add(DefrostZone.REAR);
		enumTestList.add(DefrostZone.ALL);
		enumTestList.add(DefrostZone.NONE);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}