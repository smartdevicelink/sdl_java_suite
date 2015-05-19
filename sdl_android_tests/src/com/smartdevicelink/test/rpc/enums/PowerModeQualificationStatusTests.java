package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;

public class PowerModeQualificationStatusTests extends TestCase {
	
	public void testValidEnums () {	
		String example = "POWER_MODE_UNDEFINED";
		PowerModeQualificationStatus enumPowerModeUndefined = PowerModeQualificationStatus.valueForString(example);
		example = "POWER_MODE_EVALUATION_IN_PROGRESS";
		PowerModeQualificationStatus enumPowerModeEvaluationInProgress = PowerModeQualificationStatus.valueForString(example);
		example = "NOT_DEFINED";
		PowerModeQualificationStatus enumNotDefined = PowerModeQualificationStatus.valueForString(example);
		example = "POWER_MODE_OK";
		PowerModeQualificationStatus enumPowerModeOk = PowerModeQualificationStatus.valueForString(example);
		
		assertNotNull("POWER_MODE_UNDEFINED returned null", enumPowerModeUndefined);
		assertNotNull("POWER_MODE_EVALUATION_IN_PROGRESS returned null", enumPowerModeEvaluationInProgress);
		assertNotNull("NOT_DEFINED returned null", enumNotDefined);
		assertNotNull("POWER_MODE_OK returned null", enumPowerModeOk);
	}
	
	public void testInvalidEnum () {
		String example = "poweR_moDE_UndEfiNEd";
		try {
		    PowerModeQualificationStatus temp = PowerModeQualificationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    PowerModeQualificationStatus temp = PowerModeQualificationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	public void testListEnum() {
 		List<PowerModeQualificationStatus> enumValueList = Arrays.asList(PowerModeQualificationStatus.values());

		List<PowerModeQualificationStatus> enumTestList = new ArrayList<PowerModeQualificationStatus>();
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_UNDEFINED);
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_EVALUATION_IN_PROGRESS);
		enumTestList.add(PowerModeQualificationStatus.NOT_DEFINED);
		enumTestList.add(PowerModeQualificationStatus.POWER_MODE_OK);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
