package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.IgnitionStatus}
 */
public class IgnitionStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "UNKNOWN";
		IgnitionStatus enumUnknown = IgnitionStatus.valueForString(example);
		example = "OFF";
		IgnitionStatus enumOff = IgnitionStatus.valueForString(example);
		example = "ACCESSORY";
		IgnitionStatus enumAccessory = IgnitionStatus.valueForString(example);
		example = "RUN";
		IgnitionStatus enumRun = IgnitionStatus.valueForString(example);
		example = "START";
		IgnitionStatus enumStart = IgnitionStatus.valueForString(example);
		example = "INVALID";
		IgnitionStatus enumInvalid = IgnitionStatus.valueForString(example);
		
		assertNotNull("UNKNOWN returned null", enumUnknown);
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("ACCESSORY returned null", enumAccessory);
		assertNotNull("RUN returned null", enumRun);
		assertNotNull("START returned null", enumStart);
		assertNotNull("INVALID returned null", enumInvalid);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "uNKnowN";
		try {
		    IgnitionStatus temp = IgnitionStatus.valueForString(example);
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
		    IgnitionStatus temp = IgnitionStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of IgnitionStatus.
	 */
	public void testListEnum() {
 		List<IgnitionStatus> enumValueList = Arrays.asList(IgnitionStatus.values());

		List<IgnitionStatus> enumTestList = new ArrayList<IgnitionStatus>();
		enumTestList.add(IgnitionStatus.UNKNOWN);
		enumTestList.add(IgnitionStatus.OFF);
		enumTestList.add(IgnitionStatus.ACCESSORY);
		enumTestList.add(IgnitionStatus.RUN);
		enumTestList.add(IgnitionStatus.START);
		enumTestList.add(IgnitionStatus.INVALID);		
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}