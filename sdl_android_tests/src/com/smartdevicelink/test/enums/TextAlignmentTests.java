package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TextAlignment;

public class TextAlignmentTests extends TestCase {

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
	
	public void testInvalidEnum () {
		String example = "leFT_AliGned";
		try {
			TextAlignment.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			TextAlignment.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
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
