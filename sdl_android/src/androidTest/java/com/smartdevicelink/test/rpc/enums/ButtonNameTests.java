package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ButtonName;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ButtonName}
 */
public class ButtonNameTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "OK";
		ButtonName enumOk = ButtonName.valueForString(example);
		example = "SEEKLEFT";
		ButtonName enumSeekLeft = ButtonName.valueForString(example);
		example = "SEEKRIGHT";
		ButtonName enumSeekRight = ButtonName.valueForString(example);
		example = "TUNEUP";
		ButtonName enumTuneUp = ButtonName.valueForString(example);
		example = "TUNEDOWN";
		ButtonName enumTuneDown = ButtonName.valueForString(example);
		example = "PRESET_0";
		ButtonName enumPreset0 = ButtonName.valueForString(example);
		example = "PRESET_1";
		ButtonName enumPreset1 = ButtonName.valueForString(example);
		example = "PRESET_2";
		ButtonName enumPreset2 = ButtonName.valueForString(example);
		example = "PRESET_3";
		ButtonName enumPreset3 = ButtonName.valueForString(example);
		example = "PRESET_4";
		ButtonName enumPreset4 = ButtonName.valueForString(example);
		example = "PRESET_5";
		ButtonName enumPreset5 = ButtonName.valueForString(example);
		example = "PRESET_6";
		ButtonName enumPreset6 = ButtonName.valueForString(example);
		example = "PRESET_7";
		ButtonName enumPreset7 = ButtonName.valueForString(example);
		example = "PRESET_8";
		ButtonName enumPreset8 = ButtonName.valueForString(example);
		example = "PRESET_9";
		ButtonName enumPreset9 = ButtonName.valueForString(example);
		example = "CUSTOM_BUTTON";
		ButtonName enumCustomButton = ButtonName.valueForString(example);
		example = "SEARCH";
		ButtonName enumSearch = ButtonName.valueForString(example);
		
		
		assertNotNull("OK returned null", enumOk);
		assertNotNull("SEEKLEFT returned null", enumSeekLeft);
		assertNotNull("SEEKRIGHT returned null", enumSeekRight);
		assertNotNull("TUNEUP returned null", enumTuneUp);
		assertNotNull("TUNEDOWN returned null", enumTuneDown);
		assertNotNull("PRESET_0 returned null", enumPreset0);
		assertNotNull("PRESET_1 returned null", enumPreset1);
		assertNotNull("PRESET_2 returned null", enumPreset2);
		assertNotNull("PRESET_3 returned null", enumPreset3);
		assertNotNull("PRESET_4 returned null", enumPreset4);
		assertNotNull("PRESET_5 returned null", enumPreset5);
		assertNotNull("PRESET_6 returned null", enumPreset6);
		assertNotNull("PRESET_7 returned null", enumPreset7);
		assertNotNull("PRESET_8 returned null", enumPreset8);
		assertNotNull("PRESET_9 returned null", enumPreset9);
		assertNotNull("CUSTOM_BUTTON returned null", enumCustomButton);
		assertNotNull("SEARCH returned null", enumSearch);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "oK";
		try {
		    ButtonName temp = ButtonName.valueForString(example);
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
		    ButtonName temp = ButtonName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of ButtonName.
	 */
	public void testListEnum() {
 		List<ButtonName> enumValueList = Arrays.asList(ButtonName.values());

		List<ButtonName> enumTestList = new ArrayList<ButtonName>();
		
		enumTestList.add(ButtonName.OK);
		enumTestList.add(ButtonName.SEEKLEFT);
		enumTestList.add(ButtonName.SEEKRIGHT);
		enumTestList.add(ButtonName.TUNEUP);
		enumTestList.add(ButtonName.TUNEDOWN);
		enumTestList.add(ButtonName.PRESET_0);		
		enumTestList.add(ButtonName.PRESET_1);
		enumTestList.add(ButtonName.PRESET_2);	
		enumTestList.add(ButtonName.PRESET_3);
		enumTestList.add(ButtonName.PRESET_4);	
		enumTestList.add(ButtonName.PRESET_5);	
		enumTestList.add(ButtonName.PRESET_6);
		enumTestList.add(ButtonName.PRESET_7);	
		enumTestList.add(ButtonName.PRESET_8);
		enumTestList.add(ButtonName.PRESET_9);	
		enumTestList.add(ButtonName.CUSTOM_BUTTON);	
		enumTestList.add(ButtonName.SEARCH);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	

	/**
	 * Tests the preset numeric order.
	 */
	public void testPresetIndeces () {
		ButtonName[] inputPresetArray = new ButtonName[] {
				ButtonName.PRESET_0, ButtonName.PRESET_1, ButtonName.PRESET_2, ButtonName.PRESET_3, ButtonName.PRESET_4, 
				ButtonName.PRESET_5, ButtonName.PRESET_6, ButtonName.PRESET_7, ButtonName.PRESET_8, ButtonName.PRESET_9,
				ButtonName.OK};
		
		Integer[] expectedValuesArray = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, null};

		for (int index = 0; index < inputPresetArray.length; index++) {
			ButtonName input = inputPresetArray[index];
			Integer expect = expectedValuesArray[index];
			
			Integer result = ButtonName.indexForPresetButton(input);
			assertSame("Enum input " + result + " does not match expected value " + expect, result, expect);
		}
	}

	/**
	 * Verifies that a null preset lookup is invalid.
	 */
	public void testPresetNull () {
		try {
			assertNull("Passing null as a parameter does not return null", ButtonName.indexForPresetButton(null));
		}
		catch (NullPointerException exc){
			fail("Passing null as a parameter throws a NullPointerException");
		}
		
	}
	
}
