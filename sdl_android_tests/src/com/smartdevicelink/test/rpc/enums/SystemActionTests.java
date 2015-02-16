package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SystemAction;

public class SystemActionTests extends TestCase {

	public void testValidEnums () {	
		String example = "DEFAULT_ACTION";
		SystemAction enumDefaultAction = SystemAction.valueForString(example);
		example = "STEAL_FOCUS";
		SystemAction enumStealFocus = SystemAction.valueForString(example);
		example = "KEEP_CONTEXT";
		SystemAction enumKeepContext = SystemAction.valueForString(example);
		
		assertNotNull("DEFAULT_ACTION returned null", enumDefaultAction);
		assertNotNull("STEAL_FOCUS returned null", enumStealFocus);
		assertNotNull("KEEP_CONTEXT returned null", enumKeepContext);
	}
	
	public void testInvalidEnum () {
		String example = "deFaulT_ActiON";
		try {
		    SystemAction temp = SystemAction.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    SystemAction temp = SystemAction.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<SystemAction> enumValueList = Arrays.asList(SystemAction.values());

		List<SystemAction> enumTestList = new ArrayList<SystemAction>();
		enumTestList.add(SystemAction.DEFAULT_ACTION);
		enumTestList.add(SystemAction.STEAL_FOCUS);
		enumTestList.add(SystemAction.KEEP_CONTEXT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
