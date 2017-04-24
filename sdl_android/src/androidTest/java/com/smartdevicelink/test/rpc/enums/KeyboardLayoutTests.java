package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.KeyboardLayout}
 */
public class KeyboardLayoutTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "QWERTY";
		KeyboardLayout enumQwerty = KeyboardLayout.valueForString(example);
		example = "QWERTZ";
		KeyboardLayout enumQwertz = KeyboardLayout.valueForString(example);
		example = "AZERTY";
		KeyboardLayout enumAzerty = KeyboardLayout.valueForString(example);
		
		assertNotNull("QWERTY returned null", enumQwerty);
		assertNotNull("QWERTZ returned null", enumQwertz);
		assertNotNull("AZERTY returned null", enumAzerty);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "qWerTY";
		try {
		    KeyboardLayout temp = KeyboardLayout.valueForString(example);
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
		    KeyboardLayout temp = KeyboardLayout.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of KeyboardLayout.
	 */
	public void testListEnum() {
 		List<KeyboardLayout> enumValueList = Arrays.asList(KeyboardLayout.values());

		List<KeyboardLayout> enumTestList = new ArrayList<KeyboardLayout>();
		enumTestList.add(KeyboardLayout.QWERTY);
		enumTestList.add(KeyboardLayout.QWERTZ);
		enumTestList.add(KeyboardLayout.AZERTY);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}