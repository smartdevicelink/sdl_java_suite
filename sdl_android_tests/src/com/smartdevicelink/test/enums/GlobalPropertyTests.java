package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;

public class GlobalPropertyTests extends TestCase {

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
	
	public void testInvalidEnum () {
		String example = "heLp_ProMPt";
		try {
			GlobalProperty.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			GlobalProperty.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
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
