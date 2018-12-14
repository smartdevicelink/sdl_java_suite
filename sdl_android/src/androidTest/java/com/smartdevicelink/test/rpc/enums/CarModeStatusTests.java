package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.CarModeStatus}
 */
public class CarModeStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NORMAL";
		CarModeStatus enumNormal = CarModeStatus.valueForString(example);
		example = "FACTORY";
		CarModeStatus enumFactory = CarModeStatus.valueForString(example);
		example = "TRANSPORT";
		CarModeStatus enumTransport = CarModeStatus.valueForString(example);
		example = "CRASH";
		CarModeStatus enumCrash = CarModeStatus.valueForString(example);
		
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("FACTORY returned null", enumFactory);
		assertNotNull("TRANSPORT returned null", enumTransport);
		assertNotNull("CRASH returned null", enumCrash);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "noRmaL";
		try {
		    CarModeStatus temp = CarModeStatus.valueForString(example);
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
		    CarModeStatus temp = CarModeStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of CarModeStatus.
	 */
	public void testListEnum() {
 		List<CarModeStatus> enumValueList = Arrays.asList(CarModeStatus.values());

		List<CarModeStatus> enumTestList = new ArrayList<CarModeStatus>();
		enumTestList.add(CarModeStatus.NORMAL);
		enumTestList.add(CarModeStatus.FACTORY);
		enumTestList.add(CarModeStatus.TRANSPORT);
		enumTestList.add(CarModeStatus.CRASH);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}