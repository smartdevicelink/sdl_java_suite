package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ButtonEventMode}
 */
public class ButtonEventModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "BUTTONUP";
		ButtonEventMode enumButtonUp = ButtonEventMode.valueForString(example);
		example = "BUTTONDOWN";
		ButtonEventMode enumButtonDown = ButtonEventMode.valueForString(example);
		
		assertNotNull("BUTTONUP returned null", enumButtonUp);
		assertNotNull("BUTTONDOWN returned null", enumButtonDown);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "buTTonUp";
		try {
			ButtonEventMode temp = ButtonEventMode.valueForString(example);
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
		    ButtonEventMode temp = ButtonEventMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}	

	/**
	 * Verifies the possible enum values of ButtonEventMode.
	 */
	public void testListEnum() {
 		List<ButtonEventMode> enumValueList = Arrays.asList(ButtonEventMode.values());

		List<ButtonEventMode> enumTestList = new ArrayList<ButtonEventMode>();
		enumTestList.add(ButtonEventMode.BUTTONUP);
		enumTestList.add(ButtonEventMode.BUTTONDOWN);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}