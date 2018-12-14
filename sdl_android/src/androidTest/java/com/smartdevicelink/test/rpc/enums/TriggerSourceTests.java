package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TriggerSource;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TriggerSource}
 */
public class TriggerSourceTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
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

	/**
	 * Verifies that a null assignment is invalid.
	 */
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
	

	/**
	 * Verifies the possible enum values of TriggerSource.
	 */
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