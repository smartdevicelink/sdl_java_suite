package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;

public class DeviceLevelStatusTests extends TestCase{
	//TODO: sample enum test class
	
	public void testValidEnums () {	
		String example = "ZERO_LEVEL_BARS";
		DeviceLevelStatus deviceEnumZeroLevel = DeviceLevelStatus.valueForString(example);
		example = "ONE_LEVEL_BARS";
		DeviceLevelStatus deviceEnumOneLevel = DeviceLevelStatus.valueForString(example);
		example = "TWO_LEVEL_BARS";
		DeviceLevelStatus deviceEnumTwoLevel = DeviceLevelStatus.valueForString(example);
		example = "THREE_LEVEL_BARS";
		DeviceLevelStatus deviceEnumThreeLevel = DeviceLevelStatus.valueForString(example);
		example = "FOUR_LEVEL_BARS";
		DeviceLevelStatus deviceEnumFourLevel = DeviceLevelStatus.valueForString(example);
		example = "NOT_PROVIDED";
		DeviceLevelStatus deviceEnumNotProvided = DeviceLevelStatus.valueForString(example);
				
		assertNotNull("ZERO_LEVEL_BARS returned null", deviceEnumZeroLevel);
		assertNotNull("ONE_LEVEL_BARS returned null", deviceEnumOneLevel);
		assertNotNull("TWO_LEVEL_BARS returned null", deviceEnumTwoLevel);
		assertNotNull("THREE_LEVEL_BARS returned null", deviceEnumThreeLevel);
		assertNotNull("FOUR_LEVEL_BARS returned null", deviceEnumFourLevel);
		assertNotNull("NOT_PROVIDED returned null", deviceEnumNotProvided);
	}
	
	public void testInvalidEnum () {
		String example = "onE_LeVeL_barS";
		try {
			DeviceLevelStatus.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			DeviceLevelStatus.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
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
