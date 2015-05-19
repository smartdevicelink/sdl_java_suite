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
		    TouchType temp = TouchType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
		    TouchType temp = TouchType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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
