package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;

public class KeyboardLayoutTests extends TestCase {

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
	
	public void testInvalidEnum () {
		String example = "qWerTY";
		try {
			KeyboardLayout.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			KeyboardLayout.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
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
