package com.smartdevicelink.test.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.trace.enums.InterfaceActivityDirection;

public class InterfaceActivityDirectionTests extends TestCase {

	private final String MSG = "Value did not match expected value.";
	
	public void testValidEnums () {
		
		String test = "Transmit";
		InterfaceActivityDirection enumTransmit = InterfaceActivityDirection.valueForString(test);		
		test = "Receive";
		InterfaceActivityDirection enumReceive = InterfaceActivityDirection.valueForString(test);
		test = "None";
		InterfaceActivityDirection enumNone = InterfaceActivityDirection.valueForString(test);
		
		assertNotNull(MSG, enumTransmit);
		assertNotNull(MSG, enumReceive);
		assertNotNull(MSG, enumNone);
	}

	public void testInvalidEnum () {		
		String test = "invalid";
		try {
			InterfaceActivityDirection temp = InterfaceActivityDirection.valueForString(test);
			assertNull(MSG, temp);
		} catch (IllegalArgumentException e) {
			fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	public void testNullEnum () {
		String test = null;
		try {
			InterfaceActivityDirection temp = InterfaceActivityDirection.valueForString(test);
			assertNull(MSG, temp);
		} catch (NullPointerException e) {
			fail("Invalid enum throws NullPointerException.");
		}
	}
	
	public void testListEnum () {
		List<InterfaceActivityDirection> enumValueList = Arrays.asList(InterfaceActivityDirection.values());
		List<InterfaceActivityDirection> enumTestList  = new ArrayList<InterfaceActivityDirection>();
		
		enumTestList.add(InterfaceActivityDirection.Transmit);
		enumTestList.add(InterfaceActivityDirection.Receive);
		enumTestList.add(InterfaceActivityDirection.None);
		
		assertTrue("Enum value list does not match enum class list.",
				enumValueList.containsAll(enumTestList) &&
				enumTestList.containsAll(enumValueList));		
	}
}