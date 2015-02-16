package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;

public class ButtonPressModeTests extends TestCase {

	public void testValidEnums () {	
		String example = "LONG";
		ButtonPressMode enumLong = ButtonPressMode.valueForString(example);
		example = "SHORT";
		ButtonPressMode enumShort = ButtonPressMode.valueForString(example);
		
		assertNotNull("LONG returned null", enumLong);
		assertNotNull("SHORT returned null", enumShort);
	}

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
	
	public void testListEnum() {
 		List<ButtonPressMode> enumValueList = Arrays.asList(ButtonPressMode.values());

		List<ButtonPressMode> enumTestList = new ArrayList<ButtonPressMode>();
		enumTestList.add(ButtonPressMode.LONG);
		enumTestList.add(ButtonPressMode.SHORT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
