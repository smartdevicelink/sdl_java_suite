package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.DriverDistractionState;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.DriverDistractionState}
 */
public class DriverDistractionStateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "DD_ON";
		DriverDistractionState enumDdOn = DriverDistractionState.valueForString(example);
		example = "DD_OFF";
		DriverDistractionState enumDdOff = DriverDistractionState.valueForString(example);
		
		assertNotNull("DD_ON returned null", enumDdOn);
		assertNotNull("DD_OFF returned null", enumDdOff);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "dD_oN";
		try {
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
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
		    DriverDistractionState temp = DriverDistractionState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of DriverDistractionState.
	 */
	public void testListEnum() {
 		List<DriverDistractionState> enumValueList = Arrays.asList(DriverDistractionState.values());

		List<DriverDistractionState> enumTestList = new ArrayList<DriverDistractionState>();
		enumTestList.add(DriverDistractionState.DD_ON);
		enumTestList.add(DriverDistractionState.DD_OFF);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}