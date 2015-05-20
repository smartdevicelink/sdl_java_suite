package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TextAlignment}
 */
public class TextAlignmentTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "LEFT_ALIGNED";
		TextAlignment enumLeftAligned = TextAlignment.valueForString(example);
		example = "RIGHT_ALIGNED";
		TextAlignment enumRightAligned = TextAlignment.valueForString(example);
		example = "CENTERED";
		TextAlignment enumCentered = TextAlignment.valueForString(example);
		
		assertNotNull("LEFT_ALIGNED returned null", enumLeftAligned);
		assertNotNull("RIGHT_ALIGNED returned null", enumRightAligned);
		assertNotNull("CENTERED returned null", enumCentered);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "leFT_AliGned";
		try {
		    TextAlignment temp = TextAlignment.valueForString(example);
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
		    TextAlignment temp = TextAlignment.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of TextAlignment.
	 */
	public void testListEnum() {
 		List<TextAlignment> enumValueList = Arrays.asList(TextAlignment.values());

		List<TextAlignment> enumTestList = new ArrayList<TextAlignment>();
		enumTestList.add(TextAlignment.LEFT_ALIGNED);
		enumTestList.add(TextAlignment.RIGHT_ALIGNED);
		enumTestList.add(TextAlignment.CENTERED);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}