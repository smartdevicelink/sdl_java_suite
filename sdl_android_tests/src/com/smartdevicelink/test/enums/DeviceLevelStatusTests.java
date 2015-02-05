package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;

public class DeviceLevelStatusTests extends TestCase{
	
	public void testValidEnums () {	
		String example = "ZERO_LEVEL_BARS";
		DeviceLevelStatus enumZeroLevel = DeviceLevelStatus.valueForString(example);
		example = "ONE_LEVEL_BARS";
		DeviceLevelStatus enumOneLevel = DeviceLevelStatus.valueForString(example);
		example = "TWO_LEVEL_BARS";
		DeviceLevelStatus enumTwoLevel = DeviceLevelStatus.valueForString(example);
		example = "THREE_LEVEL_BARS";
		DeviceLevelStatus enumThreeLevel = DeviceLevelStatus.valueForString(example);
		example = "FOUR_LEVEL_BARS";
		DeviceLevelStatus enumFourLevel = DeviceLevelStatus.valueForString(example);
		example = "NOT_PROVIDED";
		DeviceLevelStatus enumNotProvided = DeviceLevelStatus.valueForString(example);
				
		assertNotNull("ZERO_LEVEL_BARS returned null", enumZeroLevel);
		assertNotNull("ONE_LEVEL_BARS returned null", enumOneLevel);
		assertNotNull("TWO_LEVEL_BARS returned null", enumTwoLevel);
		assertNotNull("THREE_LEVEL_BARS returned null", enumThreeLevel);
		assertNotNull("FOUR_LEVEL_BARS returned null", enumFourLevel);
		assertNotNull("NOT_PROVIDED returned null", enumNotProvided);
	}
	
	public void testInvalidEnum () {
		String example = "onE_LeVeL_barS";
		try {
			DeviceLevelStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			DeviceLevelStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<DeviceLevelStatus> enumValueList = Arrays.asList(DeviceLevelStatus.values());
 		
		List<DeviceLevelStatus> enumTestList = new ArrayList<DeviceLevelStatus>();
		enumTestList.add(DeviceLevelStatus.ZERO_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.ONE_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.TWO_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.THREE_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.FOUR_LEVEL_BARS);
		enumTestList.add(DeviceLevelStatus.NOT_PROVIDED);		

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}

	
}
