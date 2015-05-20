package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ButtonPressMode}
 */
public class ButtonPressModeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "LONG";
		ButtonPressMode enumLong = ButtonPressMode.valueForString(example);
		example = "SHORT";
		ButtonPressMode enumShort = ButtonPressMode.valueForString(example);
		
		assertNotNull("LONG returned null", enumLong);
		assertNotNull("SHORT returned null", enumShort);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "lONg";
		try {
		    ButtonPressMode temp = ButtonPressMode.valueForString(example);
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
		    ButtonPressMode temp = ButtonPressMode.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of ButtonPressMode.
	 */
	public void testListEnum() {
 		List<ButtonPressMode> enumValueList = Arrays.asList(ButtonPressMode.values());

		List<ButtonPressMode> enumTestList = new ArrayList<ButtonPressMode>();
		enumTestList.add(ButtonPressMode.LONG);
		enumTestList.add(ButtonPressMode.SHORT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}