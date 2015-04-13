package com.smartdevicelink.test.rpc.enums;

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

	public void testInvalidEnum () {
		String example = "meNU";
		try {
		    TriggerSource temp = TriggerSource.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    TriggerSource temp = TriggerSource.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
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
