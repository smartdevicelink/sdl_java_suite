package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TouchType;

public class TouchTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "BEGIN";
		TouchType enumBegin = TouchType.valueForString(example);
		example = "MOVE";
		TouchType enumMove = TouchType.valueForString(example);
		example = "END";
		TouchType enumEnd = TouchType.valueForString(example);
		
		assertNotNull("BEGIN returned null", enumBegin);
		assertNotNull("MOVE returned null", enumMove);
		assertNotNull("END returned null", enumEnd);
	}
	
	public void testInvalidEnum () {
		String example = "bEgIn";
		try {
			TouchType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			TouchType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<TouchType> enumValueList = Arrays.asList(TouchType.values());

		List<TouchType> enumTestList = new ArrayList<TouchType>();
		enumTestList.add(TouchType.BEGIN);
		enumTestList.add(TouchType.MOVE);
		enumTestList.add(TouchType.END);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
