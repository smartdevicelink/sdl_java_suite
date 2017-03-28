package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.SoftButtonType}
 */
public class SoftButtonTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "TEXT";
		SoftButtonType enumText = SoftButtonType.valueForString(example);
		example = "IMAGE";
		SoftButtonType enumImage = SoftButtonType.valueForString(example);
		example = "BOTH";
		SoftButtonType enumBoth = SoftButtonType.valueForString(example);
		
		assertNotNull("TEXT returned null", enumText);
		assertNotNull("IMAGE returned null", enumImage);
		assertNotNull("BOTH returned null", enumBoth);
	}


	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "teXT";
		try {
		    SoftButtonType temp = SoftButtonType.valueForString(example);
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
		    SoftButtonType temp = SoftButtonType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies the possible enum values of SoftButtonType.
	 */
	public void testListEnum() {
 		List<SoftButtonType> enumValueList = Arrays.asList(SoftButtonType.values());

		List<SoftButtonType> enumTestList = new ArrayList<SoftButtonType>();
		enumTestList.add(SoftButtonType.SBT_TEXT);
		enumTestList.add(SoftButtonType.SBT_IMAGE);
		enumTestList.add(SoftButtonType.SBT_BOTH);
		
		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}