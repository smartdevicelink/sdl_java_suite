package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

public class TriggerSourceTests extends TestCase {

	public void testValidEnums () {	
		String example = "MENU";
		TriggerSource enumMenu = TriggerSource.valueForString(example);
		example = "VR";
		TriggerSource enumVr = TriggerSource.valueForString(example);
		example = "KEYBOARD";
		TriggerSource enumKeyboard = TriggerSource.valueForString(example);
		
		assertNotNull("MENU returned null", enumMenu);
		assertNotNull("VR returned null", enumVr);
		assertNotNull("KEYBOARD returned null", enumKeyboard);
	}

	//use this test if it's supposed to throw an exception
	public void testInvalidEnum () {
		String example = "meNU";
		try {
			TriggerSource.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	//use this test if it's supposed to return null
	public void testInvalidEnum2 () {
		String example = "no_FiX";
		TriggerSource result = TriggerSource.valueForString(example);
		assertNull("Invalid string didn't return null", result);
	}
	
	//use this test if it's supposed to throw an exception
	public void testNullEnum () {
		String example = null;
		try {
			TriggerSource.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	//use this test if it's supposed to return null
	public void testNullEnum2 () {
		String example = null;
		TriggerSource result = TriggerSource.valueForString(example);
		assertNull("Null string didn't return null", result);
	}
	
	public void testListEnum() {
 		List<TriggerSource> enumValueList = Arrays.asList(TriggerSource.values());

		List<TriggerSource> enumTestList = new ArrayList<TriggerSource>();
		enumTestList.add(TriggerSource.TS_MENU);
		enumTestList.add(TriggerSource.TS_VR);
		enumTestList.add(TriggerSource.TS_KEYBOARD);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
