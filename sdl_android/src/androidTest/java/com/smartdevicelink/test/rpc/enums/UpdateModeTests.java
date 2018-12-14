package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.UpdateMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.UpdateMode}
 */
public class UpdateModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "COUNTUP";
		UpdateMode enumCountUp = UpdateMode.valueForString(example);
		example = "COUNTDOWN";
		UpdateMode enumCountDown = UpdateMode.valueForString(example);
		example = "PAUSE";
		UpdateMode enumPause = UpdateMode.valueForString(example);
		example = "RESUME";
		UpdateMode enumResume = UpdateMode.valueForString(example);
		example = "CLEAR";
		UpdateMode enumClear = UpdateMode.valueForString(example);
		
		assertNotNull("COUNTUP returned null", enumCountUp);
		assertNotNull("COUNTDOWN returned null", enumCountDown);
		assertNotNull("PAUSE returned null", enumPause);
		assertNotNull("RESUME returned null", enumResume);
		assertNotNull("CLEAR returned null", enumClear);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "coUnTUp";
		try {
		    UpdateMode temp = UpdateMode.valueForString(example);
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
		    UpdateMode temp = UpdateMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of UpdateMode.
	 */
	public void testListEnum() {
 		List<UpdateMode> enumValueList = Arrays.asList(UpdateMode.values());

		List<UpdateMode> enumTestList = new ArrayList<UpdateMode>();
		enumTestList.add(UpdateMode.COUNTUP);
		enumTestList.add(UpdateMode.COUNTDOWN);
		enumTestList.add(UpdateMode.PAUSE);
		enumTestList.add(UpdateMode.RESUME);
		enumTestList.add(UpdateMode.CLEAR);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}