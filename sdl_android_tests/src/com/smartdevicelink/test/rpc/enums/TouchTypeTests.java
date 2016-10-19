package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TouchType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TouchType}
 */
public class TouchTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
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

	/**
	 * Verifies that an invalid assignment is null.
	 */
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

	/**
	 * Verifies that a null assignment is invalid.
	 */
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
	

	/**
	 * Verifies the possible enum values of TouchType.
	 */
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