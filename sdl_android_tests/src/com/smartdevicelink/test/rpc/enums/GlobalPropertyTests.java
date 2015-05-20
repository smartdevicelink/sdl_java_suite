package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.GlobalProperty}
 */
public class GlobalPropertyTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "HELPPROMPT";
		GlobalProperty enumHelpPrompt = GlobalProperty.valueForString(example);
		example = "TIMEOUTPROMPT";
		GlobalProperty enumTimeoutPrompt = GlobalProperty.valueForString(example);
		example = "VRHELPTITLE";
		GlobalProperty enumVrHelpTitle = GlobalProperty.valueForString(example);
		example = "VRHELPITEMS";
		GlobalProperty enumVrHelpItems = GlobalProperty.valueForString(example);
		example = "MENUNAME";
		GlobalProperty enumMenuName = GlobalProperty.valueForString(example);
		example = "MENUICON";
		GlobalProperty enumMenuIcon = GlobalProperty.valueForString(example);
		example = "KEYBOARDPROPERTIES";
		GlobalProperty enumKeyboardProperties = GlobalProperty.valueForString(example);
		
		assertNotNull("HELPPROMPT returned null", enumHelpPrompt);
		assertNotNull("TIMEOUTPROMPT returned null", enumTimeoutPrompt);
		assertNotNull("VRHELPTITLE returned null", enumVrHelpTitle);
		assertNotNull("VRHELPITEMS returned null", enumVrHelpItems);
		assertNotNull("MENUNAME returned null", enumMenuName);
		assertNotNull("MENUICON returned null", enumMenuIcon);
		assertNotNull("KEYBOARDPROPERTIES returned null", enumKeyboardProperties);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "heLp_ProMPt";
		try {
		    GlobalProperty temp = GlobalProperty.valueForString(example);
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
		    GlobalProperty temp = GlobalProperty.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of GlobalProperty.
	 */
	public void testListEnum() {
 		List<GlobalProperty> enumValueList = Arrays.asList(GlobalProperty.values());

		List<GlobalProperty> enumTestList = new ArrayList<GlobalProperty>();
		enumTestList.add(GlobalProperty.HELPPROMPT);
		enumTestList.add(GlobalProperty.TIMEOUTPROMPT);
		enumTestList.add(GlobalProperty.VRHELPTITLE);
		enumTestList.add(GlobalProperty.VRHELPITEMS);
		enumTestList.add(GlobalProperty.MENUNAME);
		enumTestList.add(GlobalProperty.MENUICON);		
		enumTestList.add(GlobalProperty.KEYBOARDPROPERTIES);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}