package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SystemContext;

public class SystemContextTests extends TestCase {

	public void testValidEnums () {	
		String example = "MAIN";
		SystemContext enumMain = SystemContext.valueForString(example);
		example = "VRSESSION";
		SystemContext enumVrSession = SystemContext.valueForString(example);
		example = "MENU";
		SystemContext enumMenu = SystemContext.valueForString(example);
		example = "HMI_OBSCURED";
		SystemContext enumHmiObscured = SystemContext.valueForString(example);
		example = "ALERT";
		SystemContext enumAlert = SystemContext.valueForString(example);
		
		assertNotNull("MAIN returned null", enumMain);
		assertNotNull("VRSESSION returned null", enumVrSession);
		assertNotNull("MENU returned null", enumMenu);
		assertNotNull("HMI_OBSCURED returned null", enumHmiObscured);
		assertNotNull("ALERT returned null", enumAlert);
	}

	public void testInvalidEnum () {
		String example = "mAIn";
		try {
		    SystemContext temp = SystemContext.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    SystemContext temp = SystemContext.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	public void testListEnum() {
 		List<SystemContext> enumValueList = Arrays.asList(SystemContext.values());

		List<SystemContext> enumTestList = new ArrayList<SystemContext>();
		enumTestList.add(SystemContext.SYSCTXT_MAIN);
		enumTestList.add(SystemContext.SYSCTXT_VRSESSION);
		enumTestList.add(SystemContext.SYSCTXT_MENU);
		enumTestList.add(SystemContext.SYSCTXT_HMI_OBSCURED);
		enumTestList.add(SystemContext.SYSCTXT_ALERT);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
