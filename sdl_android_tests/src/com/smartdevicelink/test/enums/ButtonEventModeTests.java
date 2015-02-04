package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;

public class ButtonEventModeTests extends TestCase {

	public void testValidEnums () {	
		String example = "BUTTONUP";
		ButtonEventMode enumButtonUp = ButtonEventMode.valueForString(example);
		example = "BUTTONDOWN";
		ButtonEventMode enumButtonDown = ButtonEventMode.valueForString(example);
		
		assertNotNull("BUTTONUP returned null", enumButtonUp);
		assertNotNull("BUTTONDOWN returned null", enumButtonDown);
	}
	
	public void testInvalidEnum () {
		String example = "buTTonUp";
		try {
			ButtonEventMode.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			ButtonEventMode.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<ButtonEventMode> enumValueList = Arrays.asList(ButtonEventMode.values());

		List<ButtonEventMode> enumTestList = new ArrayList<ButtonEventMode>();
		enumTestList.add(ButtonEventMode.BUTTONUP);
		enumTestList.add(ButtonEventMode.BUTTONDOWN);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
