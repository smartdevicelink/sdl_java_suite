package com.smartdevicelink.test.trace.enums;

import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.trace.enums.InterfaceActivityDirection;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.trace.enums.InterfaceActivityDirection}
 */
public class InterfaceActivityDirectionTests extends TestCase {
	
	/**
	 * This is a unit test for the following enum : 
	 * {@link com.smartdevicelink.trace.enums.InterfaceActivityDirection}
	 */
	public void testInterfaceActivityDirectionEnum () {
		
		// Test Values
		String testNone     = "None";
		String testInvalid  = "Invalid";
		String testReceive  = "Receive";
		String testTransmit	= "Transmit";
		
		try {
			// Comparison Values
			InterfaceActivityDirection expectedNoneEnum       = InterfaceActivityDirection.None;
			InterfaceActivityDirection expectedReceiveEnum    = InterfaceActivityDirection.Receive;
			InterfaceActivityDirection expectedTransmitEnum   = InterfaceActivityDirection.Transmit;
			List<InterfaceActivityDirection> expectedEnumList = new ArrayList<InterfaceActivityDirection>();
			expectedEnumList.add(InterfaceActivityDirection.None);
			expectedEnumList.add(InterfaceActivityDirection.Receive);
			expectedEnumList.add(InterfaceActivityDirection.Transmit);
			
			InterfaceActivityDirection actualNullEnum       = InterfaceActivityDirection.valueForString(null);
			InterfaceActivityDirection actualNoneEnum       = InterfaceActivityDirection.valueForString(testNone);
			InterfaceActivityDirection actualInvalidEnum    = InterfaceActivityDirection.valueForString(testInvalid);
			InterfaceActivityDirection actualReceiveEnum    = InterfaceActivityDirection.valueForString(testReceive);
			InterfaceActivityDirection actualTransmitEnum   = InterfaceActivityDirection.valueForString(testTransmit);
			List<InterfaceActivityDirection> actualEnumList = Arrays.asList(InterfaceActivityDirection.values());
			
			// Valid Tests
			assertEquals(TestValues.MATCH, expectedNoneEnum, actualNoneEnum);
			assertEquals(TestValues.MATCH, expectedReceiveEnum, actualReceiveEnum);
			assertEquals(TestValues.MATCH, expectedTransmitEnum, actualTransmitEnum);
			assertTrue(TestValues.ARRAY, expectedEnumList.containsAll(actualEnumList) && actualEnumList.containsAll(expectedEnumList));
						
			// Invalid/Null 
			assertNull(TestValues.NULL, actualInvalidEnum);
			assertNull(TestValues.NULL, actualNullEnum);
			
		} catch (NullPointerException e) {
			fail("Could not retrieve value for null string, should return null.");
		} catch (IllegalArgumentException e) {
			fail("Could not retrieve value for invalid string, should return null.");
		}	
	}
}