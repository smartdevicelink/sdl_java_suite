package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.UpdateMode;

public class UpdateModeTests extends TestCase {

	public void testValidEnums () {	
		String example = "COUNTUP";
		UpdateMode enumCountUp = UpdateMode.valueForString(example);
		example = "COUNTDOWN";
		UpdateMode enumCountDown = UpdateMode.valueForString(example);
		example = "PAUSE";
		UpdateMode enumPause = UpdateMode.valueForString(example);
		example = "RESUME";
		UpdateMode enumResume = UpdateMode.valueForString(example);
		example = "CLEAR";
		UpdateMode enumClear = UpdateMode.valueForString(example);
		
		assertNotNull("COUNTUP returned null", enumCountUp);
		assertNotNull("COUNTDOWN returned null", enumCountDown);
		assertNotNull("PAUSE returned null", enumPause);
		assertNotNull("RESUME returned null", enumResume);
		assertNotNull("CLEAR returned null", enumClear);
	}
	
	public void testInvalidEnum () {
		String example = "coUnTUp";
		try {
			UpdateMode.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			UpdateMode.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<UpdateMode> enumValueList = Arrays.asList(UpdateMode.values());

		List<UpdateMode> enumTestList = new ArrayList<UpdateMode>();
		enumTestList.add(UpdateMode.COUNTUP);
		enumTestList.add(UpdateMode.COUNTDOWN);
		enumTestList.add(UpdateMode.PAUSE);
		enumTestList.add(UpdateMode.RESUME);
		enumTestList.add(UpdateMode.CLEAR);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
