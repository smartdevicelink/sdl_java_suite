package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.WarningListStatus}
 */
public class WarningLightStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "OFF";
		WarningLightStatus enumOff = WarningLightStatus.valueForString(example);
		example = "ON";
		WarningLightStatus enumOn = WarningLightStatus.valueForString(example);
		example = "FLASH";
		WarningLightStatus enumFlash = WarningLightStatus.valueForString(example);
		example = "NOT_USED";
		WarningLightStatus enumNotUsed = WarningLightStatus.valueForString(example);
		
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ON returned null", enumOn);
		assertNotNull("FLASH returned null", enumFlash);
		assertNotNull("NOT_USED returned null", enumNotUsed);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "OfF";
		try {
		    WarningLightStatus temp = WarningLightStatus.valueForString(example);
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
		    WarningLightStatus temp = WarningLightStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of WarningLightStatus.
	 */
	public void testListEnum() {
 		List<WarningLightStatus> enumValueList = Arrays.asList(WarningLightStatus.values());

		List<WarningLightStatus> enumTestList = new ArrayList<WarningLightStatus>();
		enumTestList.add(WarningLightStatus.OFF);
		enumTestList.add(WarningLightStatus.ON);
		enumTestList.add(WarningLightStatus.FLASH);
		enumTestList.add(WarningLightStatus.NOT_USED);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}