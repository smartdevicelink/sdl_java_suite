package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.LightSwitchStatus;

public class LightSwitchStatusTests extends TestCase {

	public void testValidEnums () {	
		String example = "OFF";
		LightSwitchStatus enumOff = LightSwitchStatus.valueForString(example);
		example = "PARKLAMP";
		LightSwitchStatus enumParklamp = LightSwitchStatus.valueForString(example);
		example = "HEADLAMP";
		LightSwitchStatus enumHeadlamp = LightSwitchStatus.valueForString(example);
		example = "AUTOLAMP";
		LightSwitchStatus enumAutolamp = LightSwitchStatus.valueForString(example);
		
		assertNotNull("OFF returned null", enumOff);
		assertNotNull("PARKLAMP returned null", enumParklamp);
		assertNotNull("HEADLAMP returned null", enumHeadlamp);
		assertNotNull("AUTOLAMP returned null", enumAutolamp);
	}
	
	public void testInvalidEnum () {
		String example = "oFf";
		try {
		    LightSwitchStatus temp = LightSwitchStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    LightSwitchStatus temp = LightSwitchStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<LightSwitchStatus> enumValueList = Arrays.asList(LightSwitchStatus.values());

		List<LightSwitchStatus> enumTestList = new ArrayList<LightSwitchStatus>();
		enumTestList.add(LightSwitchStatus.OFF);
		enumTestList.add(LightSwitchStatus.PARKLAMP);
		enumTestList.add(LightSwitchStatus.HEADLAMP);
		enumTestList.add(LightSwitchStatus.AUTOLAMP);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
