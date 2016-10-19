package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.HmiLevel}
 */
public class HmiLevelTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "FULL";
		HMILevel enumFull = HMILevel.valueForString(example);
		example = "LIMITED";
		HMILevel enumLimited = HMILevel.valueForString(example);
		example = "BACKGROUND";
		HMILevel enumBackground = HMILevel.valueForString(example);
		example = "NONE";
		HMILevel enumNone = HMILevel.valueForString(example);
		
		assertNotNull("FULL returned null", enumFull);
		assertNotNull("LIMITED returned null", enumLimited);
		assertNotNull("BACKGROUND returned null", enumBackground);
		assertNotNull("NONE returned null", enumNone);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "fUlL";
		try {
		    HMILevel temp = HMILevel.valueForString(example);
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
		    HMILevel temp = HMILevel.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of HMILevel.
	 */
	public void testListEnum() {
 		List<HMILevel> enumValueList = Arrays.asList(HMILevel.values());

		List<HMILevel> enumTestList = new ArrayList<HMILevel>();
		enumTestList.add(HMILevel.HMI_FULL);
		enumTestList.add(HMILevel.HMI_LIMITED);
		enumTestList.add(HMILevel.HMI_BACKGROUND);
		enumTestList.add(HMILevel.HMI_NONE);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}